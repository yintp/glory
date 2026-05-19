import sqlite3
import pandas as pd
from app.database import init_db, get_conn, upsert_daily, upsert_indicators, upsert_holding


def test_init_creates_all_tables(tmp_db):
    conn = get_conn()
    c = conn.cursor()
    c.execute("SELECT name FROM sqlite_master WHERE type='table' ORDER BY name")
    tables = [r[0] for r in c.fetchall()]
    conn.close()
    assert tables == ['etf_daily', 'etf_holding', 'etf_indicators', 'etf_list']


def test_upsert_daily_idempotent(tmp_db):
    conn = get_conn()
    df = pd.DataFrame([
        {'date': '2024-01-02', 'open': 1.0, 'high': 1.1, 'low': 0.9, 'close': 1.05, 'volume': 1000.0},
        {'date': '2024-01-03', 'open': 1.05, 'high': 1.15, 'low': 1.0, 'close': 1.10, 'volume': 1200.0},
    ])
    upsert_daily(conn, '512480', df)
    upsert_daily(conn, '512480', df)  # second call must not fail
    c = conn.cursor()
    c.execute("SELECT COUNT(*) FROM etf_daily WHERE symbol='512480'")
    assert c.fetchone()[0] == 2
    conn.close()


def test_upsert_indicators_handles_nan(tmp_db):
    import numpy as np
    conn = get_conn()
    df = pd.DataFrame([
        {'date': '2024-01-02', 'ma20': float('nan'), 'w_ma20': None, 'm_ma12': 1.0,
         'vol_ma20': float('nan'), 'macd': 0.01, 'macd_signal': 0.005, 'macd_hist': 0.005,
         'buy_signal': 0, 'sell_signal': 0}
    ])
    upsert_indicators(conn, '512480', df)
    c = conn.cursor()
    c.execute("SELECT ma20, w_ma20 FROM etf_indicators WHERE symbol='512480'")
    row = c.fetchone()
    assert row[0] is None
    assert row[1] is None
    conn.close()


def test_upsert_holding(tmp_db):
    conn = get_conn()
    upsert_holding(conn, '512480', '2025Q3', 68.5, 31.5)
    c = conn.cursor()
    c.execute("SELECT inst_ratio FROM etf_holding WHERE symbol='512480'")
    assert c.fetchone()[0] == 68.5
    conn.close()


from app.services.heat import compute_heat_scores
from datetime import datetime


def _seed_etf_with_volume(conn, symbol, volume_today, vol_ma20):
    conn.execute(
        "INSERT OR REPLACE INTO etf_list (symbol, name, active, created_at) VALUES (?,?,1,?)",
        (symbol, f"Test {symbol}", datetime.now().strftime('%Y-%m-%d %H:%M:%S'))
    )
    conn.execute(
        "INSERT OR REPLACE INTO etf_daily (symbol, date, open, high, low, close, volume) "
        "VALUES (?,?,1.0,1.1,0.9,1.0,?)",
        (symbol, '2024-05-10', volume_today)
    )
    conn.execute(
        "INSERT OR REPLACE INTO etf_indicators "
        "(symbol, date, vol_ma20, buy_signal, sell_signal) VALUES (?,?,?,0,0)",
        (symbol, '2024-05-10', vol_ma20)
    )
    conn.commit()


def test_compute_heat_scores_sets_ratio(tmp_db):
    conn = get_conn()
    _seed_etf_with_volume(conn, 'A', volume_today=2_000_000.0, vol_ma20=1_000_000.0)
    compute_heat_scores(conn)
    c = conn.cursor()
    c.execute("SELECT heat_score FROM etf_indicators WHERE symbol='A'")
    score = c.fetchone()[0]
    assert abs(score - 2.0) < 0.001
    conn.close()
