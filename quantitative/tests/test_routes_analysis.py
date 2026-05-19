from unittest.mock import patch
import pandas as pd
from datetime import datetime
import app.database as db_module


def _seed_full_etf(tmp_db):
    """Insert ETF list entry + 60 days of daily data + indicators."""
    conn = db_module.get_conn()
    conn.execute(
        "INSERT INTO etf_list (symbol, name, active, created_at) VALUES (?,?,1,?)",
        ('512480', '半导体ETF', datetime.now().strftime('%Y-%m-%d %H:%M:%S'))
    )
    conn.commit()
    dates = pd.date_range('2024-01-02', periods=60, freq='B').strftime('%Y-%m-%d').tolist()
    close = list(range(100, 160))
    df_d = pd.DataFrame({
        'date': dates, 'open': [c - 0.5 for c in close],
        'high': [c + 0.5 for c in close], 'low': [c - 1.0 for c in close],
        'close': [float(c) for c in close], 'volume': [1_000_000.0] * 60,
    })
    db_module.upsert_daily(conn, '512480', df_d)
    from app.services.indicators import calculate_indicators
    df_ind = calculate_indicators(df_d)
    db_module.upsert_indicators(conn, '512480', df_ind)
    db_module.upsert_holding(conn, '512480', '2025Q1', 65.0, 35.0)
    conn.close()


def test_get_etf_data_returns_rows(client, tmp_db):
    _seed_full_etf(tmp_db)
    r = client.get("/api/etf/512480/data")
    assert r.status_code == 200
    body = r.json()
    assert len(body['data']) == 60
    assert 'holding' in body


def test_get_etf_data_time_range_filters(client, tmp_db):
    _seed_full_etf(tmp_db)
    r = client.get("/api/etf/512480/data?start=2024-02-01&end=2024-02-29")
    assert r.status_code == 200
    for row in r.json()['data']:
        assert row['date'] >= '2024-02-01'
        assert row['date'] <= '2024-02-29'


def test_get_etf_data_404_for_unknown(client):
    r = client.get("/api/etf/000000/data")
    assert r.status_code == 404


def test_heat_rank_returns_sorted_list(client, tmp_db):
    _seed_full_etf(tmp_db)
    from app.services.heat import compute_heat_scores
    conn = db_module.get_conn()
    compute_heat_scores(conn)
    conn.close()
    r = client.get("/api/heat/rank")
    assert r.status_code == 200
    data = r.json()
    assert len(data) >= 1
    assert 'heat_score' in data[0]
