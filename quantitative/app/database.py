import sqlite3
import math
import os
import pandas as pd
from datetime import datetime

DB_PATH = os.path.join(os.path.dirname(__file__), '..', 'etf.db')


def get_conn() -> sqlite3.Connection:
    conn = sqlite3.connect(DB_PATH)
    conn.row_factory = sqlite3.Row
    return conn


def init_db():
    conn = get_conn()
    try:
        conn.executescript("""
            CREATE TABLE IF NOT EXISTS etf_list (
                symbol TEXT PRIMARY KEY,
                name   TEXT NOT NULL,
                active INTEGER NOT NULL DEFAULT 1,
                created_at TEXT NOT NULL
            );
            CREATE TABLE IF NOT EXISTS etf_daily (
                symbol TEXT NOT NULL,
                date   TEXT NOT NULL,
                open   REAL,
                high   REAL,
                low    REAL,
                close  REAL NOT NULL,
                volume REAL NOT NULL,
                PRIMARY KEY (symbol, date)
            );
            CREATE TABLE IF NOT EXISTS etf_indicators (
                symbol     TEXT NOT NULL,
                date       TEXT NOT NULL,
                ma20       REAL,
                w_ma20     REAL,
                m_ma12     REAL,
                vol_ma20   REAL,
                macd       REAL,
                macd_signal REAL,
                macd_hist  REAL,
                heat_score REAL,
                buy_signal INTEGER,
                sell_signal INTEGER,
                PRIMARY KEY (symbol, date)
            );
            CREATE TABLE IF NOT EXISTS etf_holding (
                symbol      TEXT NOT NULL,
                period      TEXT NOT NULL,
                inst_ratio  REAL,
                retail_ratio REAL,
                updated_at  TEXT NOT NULL,
                PRIMARY KEY (symbol, period)
            );
        """)
        conn.commit()
    finally:
        conn.close()


def _n(v):
    """Convert NaN/None to None for SQLite storage."""
    if v is None:
        return None
    try:
        f = float(v)
        if math.isnan(f):
            return None
        return f
    except (TypeError, ValueError):
        return None


def _to_int(v):
    """Convert value to int, handling NaN/None/string inputs safely."""
    try:
        f = float(v) if v is not None else 0.0
        return 0 if math.isnan(f) else int(f)
    except (TypeError, ValueError):
        return 0


def upsert_daily(conn: sqlite3.Connection, symbol: str, df: pd.DataFrame):
    c = conn.cursor()
    for _, row in df.iterrows():
        # Validate required fields: close and volume
        try:
            close_val = float(row['close'])
            vol_val = float(row['volume'])
            if math.isnan(close_val) or math.isnan(vol_val):
                continue  # skip rows with missing required fields
        except (TypeError, ValueError):
            continue  # skip rows with invalid required fields

        c.execute(
            "INSERT OR REPLACE INTO etf_daily "
            "(symbol, date, open, high, low, close, volume) VALUES (?,?,?,?,?,?,?)",
            (symbol, row['date'], _n(row.get('open')), _n(row.get('high')),
             _n(row.get('low')), close_val, vol_val)
        )
    conn.commit()


def upsert_indicators(conn: sqlite3.Connection, symbol: str, df: pd.DataFrame):
    c = conn.cursor()
    for _, row in df.iterrows():
        c.execute(
            "INSERT OR REPLACE INTO etf_indicators "
            "(symbol, date, ma20, w_ma20, m_ma12, vol_ma20, "
            " macd, macd_signal, macd_hist, heat_score, buy_signal, sell_signal) "
            "VALUES (?,?,?,?,?,?,?,?,?,?,?,?)",
            (symbol, row['date'],
             _n(row.get('ma20')), _n(row.get('w_ma20')), _n(row.get('m_ma12')),
             _n(row.get('vol_ma20')), _n(row.get('macd')),
             _n(row.get('macd_signal')), _n(row.get('macd_hist')),
             _n(row.get('heat_score')),
             _to_int(row.get('buy_signal')), _to_int(row.get('sell_signal')))
        )
    conn.commit()


def upsert_holding(conn: sqlite3.Connection, symbol: str, period: str,
                   inst_ratio: float, retail_ratio: float):
    conn.execute(
        "INSERT OR REPLACE INTO etf_holding "
        "(symbol, period, inst_ratio, retail_ratio, updated_at) VALUES (?,?,?,?,?)",
        (symbol, period, inst_ratio, retail_ratio,
         datetime.now().strftime('%Y-%m-%d %H:%M:%S'))
    )
    conn.commit()
