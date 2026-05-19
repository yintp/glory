from unittest.mock import patch
import pandas as pd
from datetime import datetime
import app.database as db_module


def _add_etf_to_db(tmp_db, symbol='512480'):
    conn = db_module.get_conn()
    conn.execute(
        "INSERT INTO etf_list (symbol, name, active, created_at) VALUES (?,?,1,?)",
        (symbol, 'Test ETF', datetime.now().strftime('%Y-%m-%d %H:%M:%S'))
    )
    conn.commit()
    conn.close()


def _fake_df(symbol='512480'):
    dates = pd.date_range('2024-01-02', periods=60, freq='B').strftime('%Y-%m-%d').tolist()
    close = list(range(100, 160))
    return pd.DataFrame({
        'date': dates, 'open': [c - 0.5 for c in close],
        'high': [c + 0.5 for c in close], 'low': [c - 1.0 for c in close],
        'close': [float(c) for c in close], 'volume': [1_000_000.0] * 60,
    })


def test_update_status_initially_idle(client):
    r = client.get("/api/update/status")
    assert r.status_code == 200
    assert r.json()['status'] == 'idle'


def test_trigger_update_success(client, tmp_db):
    _add_etf_to_db(tmp_db)
    with patch('app.routes.update.fetch_etf_ohlcv', return_value=_fake_df()):
        r = client.post("/api/update")
    assert r.status_code == 200
    body = r.json()
    assert body['updated'] == 1
    assert body['failed'] == 0


def test_trigger_update_handles_fetch_failure(client, tmp_db):
    _add_etf_to_db(tmp_db)
    with patch('app.routes.update.fetch_etf_ohlcv', return_value=None):
        r = client.post("/api/update")
    assert r.status_code == 200
    body = r.json()
    assert body['updated'] == 0
    assert body['failed'] == 1
