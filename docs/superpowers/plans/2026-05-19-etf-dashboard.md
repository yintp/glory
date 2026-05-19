# ETF 本地分析平台 Implementation Plan

> **For agentic workers:** REQUIRED SUB-SKILL: Use superpowers:subagent-driven-development (recommended) or superpowers:executing-plans to implement this plan task-by-task. Steps use checkbox (`- [ ]`) syntax for tracking.

**Goal:** 将 quantitative/helloword.py 改造为可通过浏览器访问的本地 ETF 分析平台，支持 CRUD、技术指标图表、热度排行和每日手动数据更新。

**Architecture:** FastAPI 后端提供 REST API，SQLite 存储 OHLCV 数据和计算好的技术指标，原生 HTML+JS 前端用 Chart.js 渲染图表。akshare 数据拉取逻辑从现有 helloword.py 复用。

**Tech Stack:** Python 3.10+, FastAPI, uvicorn, akshare, pandas, ta, sqlite3, Chart.js 4.x, chartjs-chart-financial

---

## File Map

```
quantitative/
├── app/
│   ├── __init__.py
│   ├── main.py                  # FastAPI 入口，挂载静态文件，注册路由
│   ├── database.py              # get_conn, init_db, upsert_daily, upsert_indicators, upsert_holding
│   ├── services/
│   │   ├── __init__.py
│   │   ├── fetcher.py           # fetch_etf_ohlcv, fetch_etf_holding (wraps akshare)
│   │   ├── indicators.py        # calculate_indicators(df) → df with all indicator columns
│   │   └── heat.py              # compute_heat_scores(conn)
│   └── routes/
│       ├── __init__.py
│       ├── etf.py               # GET/POST/PUT/DELETE /api/etf
│       ├── analysis.py          # GET /api/etf/{symbol}/data, GET /api/heat/rank
│       └── update.py            # POST /api/update, GET /api/update/status
├── frontend/
│   ├── index.html               # 单页 4 Tab 入口
│   ├── js/
│   │   ├── api.js               # 所有 fetch 调用封装
│   │   ├── charts.js            # Chart.js 图表定义（K线/MACD/成交量）
│   │   └── app.js               # Tab 路由、ETF 选择、时间区间交互
│   └── css/
│       └── style.css            # 暗色主题
├── tests/
│   ├── __init__.py
│   ├── conftest.py              # pytest fixtures: tmp_db, client
│   ├── test_database.py
│   ├── test_indicators.py
│   ├── test_routes_etf.py
│   ├── test_routes_analysis.py
│   └── test_routes_update.py
├── requirements.txt
└── helloword.py                 # 保留不动
```

---

## Task 1: 项目脚手架

**Files:**
- Create: `quantitative/requirements.txt`
- Create: `quantitative/app/__init__.py`
- Create: `quantitative/app/services/__init__.py`
- Create: `quantitative/app/routes/__init__.py`
- Create: `quantitative/tests/__init__.py`
- Create: `quantitative/tests/conftest.py`

- [ ] **Step 1: 创建目录结构**

```bash
cd quantitative
mkdir -p app/services app/routes frontend/js frontend/css tests
```

- [ ] **Step 2: 写 requirements.txt**

```
fastapi==0.111.0
uvicorn==0.29.0
akshare>=1.14.0
pandas>=2.0.0
ta>=0.11.0
httpx==0.27.0
pytest==8.2.0
```

- [ ] **Step 3: 写所有 `__init__.py`（全部空文件）**

```bash
touch app/__init__.py app/services/__init__.py app/routes/__init__.py tests/__init__.py
```

- [ ] **Step 4: 写 `tests/conftest.py`**

```python
import pytest
import app.database as db_module
from fastapi.testclient import TestClient


@pytest.fixture
def tmp_db(tmp_path):
    db_file = str(tmp_path / "test.db")
    original = db_module.DB_PATH
    db_module.DB_PATH = db_file
    db_module.init_db()
    yield db_file
    db_module.DB_PATH = original


@pytest.fixture
def client(tmp_db):
    from app.main import app
    return TestClient(app)
```

- [ ] **Step 5: 安装依赖**

```bash
pip install -r requirements.txt
```

---

## Task 2: 数据库层

**Files:**
- Create: `quantitative/app/database.py`
- Test: `quantitative/tests/test_database.py`

- [ ] **Step 1: 写失败测试**

`tests/test_database.py`:
```python
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
```

- [ ] **Step 2: 运行测试确认失败**

```bash
cd quantitative
pytest tests/test_database.py -v
```
Expected: `ModuleNotFoundError: No module named 'app.database'`

- [ ] **Step 3: 写 `app/database.py`**

```python
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
    conn.close()


def _n(v):
    """Convert NaN/None to None for SQLite storage."""
    if v is None:
        return None
    try:
        if math.isnan(float(v)):
            return None
    except (TypeError, ValueError):
        pass
    return float(v)


def upsert_daily(conn: sqlite3.Connection, symbol: str, df: pd.DataFrame):
    c = conn.cursor()
    for _, row in df.iterrows():
        c.execute(
            "INSERT OR REPLACE INTO etf_daily "
            "(symbol, date, open, high, low, close, volume) VALUES (?,?,?,?,?,?,?)",
            (symbol, row['date'], _n(row.get('open')), _n(row.get('high')),
             _n(row.get('low')), float(row['close']), float(row['volume']))
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
             int(row.get('buy_signal') or 0), int(row.get('sell_signal') or 0))
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
```

- [ ] **Step 4: 运行测试确认通过**

```bash
pytest tests/test_database.py -v
```
Expected: 4 PASSED

- [ ] **Step 5: 提交**

```bash
git add app/database.py tests/test_database.py tests/conftest.py requirements.txt
git commit -m "feat(etf-dashboard): add database layer with init and upsert helpers"
```

---

## Task 3: 指标计算服务

**Files:**
- Create: `quantitative/app/services/indicators.py`
- Test: `quantitative/tests/test_indicators.py`

- [ ] **Step 1: 写失败测试**

`tests/test_indicators.py`:
```python
import pandas as pd
import numpy as np
from app.services.indicators import calculate_indicators


def _make_df(n=60):
    """Generate n days of fake OHLCV starting 2023-01-02."""
    dates = pd.date_range('2023-01-02', periods=n, freq='B')
    close = pd.Series(range(100, 100 + n), dtype=float)
    return pd.DataFrame({
        'date': dates.strftime('%Y-%m-%d'),
        'open': close - 0.5,
        'high': close + 0.5,
        'low': close - 1.0,
        'close': close,
        'volume': pd.Series([1_000_000.0] * n),
    })


def test_output_has_required_columns():
    df = calculate_indicators(_make_df())
    for col in ['ma20', 'vol_ma20', 'macd', 'macd_signal', 'macd_hist',
                'w_ma20', 'm_ma12', 'buy_signal', 'sell_signal']:
        assert col in df.columns, f"Missing column: {col}"


def test_ma20_is_nan_for_first_19_rows():
    df = calculate_indicators(_make_df(60))
    assert df['ma20'].iloc[:19].isna().all()
    assert not pd.isna(df['ma20'].iloc[19])


def test_buy_sell_signals_are_binary():
    df = calculate_indicators(_make_df(120))
    assert set(df['buy_signal'].dropna().unique()).issubset({0, 1})
    assert set(df['sell_signal'].dropna().unique()).issubset({0, 1})


def test_date_column_preserved_as_string():
    df = calculate_indicators(_make_df())
    assert df['date'].dtype == object
    assert df['date'].iloc[0] == '2023-01-02'
```

- [ ] **Step 2: 运行测试确认失败**

```bash
pytest tests/test_indicators.py -v
```
Expected: `ModuleNotFoundError: No module named 'app.services.indicators'`

- [ ] **Step 3: 写 `app/services/indicators.py`**

```python
import pandas as pd
import numpy as np
from ta.trend import macd as ta_macd, macd_signal as ta_macd_signal


def calculate_indicators(df: pd.DataFrame) -> pd.DataFrame:
    """
    Compute all technical indicators.
    Input: df with columns date(str), open, high, low, close, volume — sorted ascending.
    Output: same df with added indicator columns. date stays as YYYY-MM-DD string.
    """
    df = df.copy()
    df['date'] = pd.to_datetime(df['date'])
    df.sort_values('date', inplace=True)
    df.set_index('date', inplace=True)

    # Daily MA20 and volume MA20
    df['ma20'] = df['close'].rolling(20).mean()
    df['vol_ma20'] = df['volume'].rolling(20).mean()

    # MACD (12, 26, 9)
    df['macd'] = ta_macd(df['close'])
    df['macd_signal'] = ta_macd_signal(df['close'])
    df['macd_hist'] = df['macd'] - df['macd_signal']

    # Weekly MA20 — resample to Friday-end weeks, compute rolling, ffill back
    weekly = df[['close']].resample('W-FRI').last()
    weekly['w_ma20'] = weekly['close'].rolling(20).mean()
    df['w_ma20'] = np.nan
    for idx in weekly.index:
        if idx in df.index:
            df.at[idx, 'w_ma20'] = weekly.at[idx, 'w_ma20']
    df['w_ma20'] = df['w_ma20'].ffill()

    # Monthly MA12 — resample to month-end, compute rolling, ffill back
    monthly = df[['close']].resample('ME').last()
    monthly['m_ma12'] = monthly['close'].rolling(12).mean()
    df['m_ma12'] = np.nan
    for idx in monthly.index:
        if idx in df.index:
            df.at[idx, 'm_ma12'] = monthly.at[idx, 'm_ma12']
    df['m_ma12'] = df['m_ma12'].ffill()

    # Buy signal: w_ma20 crosses above m_ma12
    # Sell signal: w_ma20 crosses below m_ma12
    w_above = df['w_ma20'] > df['m_ma12']
    prev_above = w_above.shift(1).fillna(w_above)
    df['buy_signal'] = ((w_above) & (~prev_above)).astype(int)
    df['sell_signal'] = ((~w_above) & (prev_above)).astype(int)

    df.reset_index(inplace=True)
    df['date'] = df['date'].dt.strftime('%Y-%m-%d')
    return df
```

- [ ] **Step 4: 运行测试确认通过**

```bash
pytest tests/test_indicators.py -v
```
Expected: 4 PASSED

- [ ] **Step 5: 提交**

```bash
git add app/services/indicators.py tests/test_indicators.py
git commit -m "feat(etf-dashboard): add indicators service (MA/MACD/signals)"
```

---

## Task 4: 数据拉取服务

**Files:**
- Create: `quantitative/app/services/fetcher.py`
- Test: `quantitative/tests/test_fetcher.py` *(用 mock，不发真实网络请求)*

- [ ] **Step 1: 写 `tests/test_fetcher.py`**

```python
from unittest.mock import patch
import pandas as pd
from app.services.fetcher import fetch_etf_ohlcv, fetch_etf_holding


def _fake_df():
    return pd.DataFrame({
        'date': ['2024-01-02', '2024-01-03'],
        'open': [1.0, 1.05],
        'high': [1.1, 1.15],
        'low': [0.9, 1.0],
        'close': [1.05, 1.10],
        'volume': [1_000_000.0, 1_200_000.0],
    })


def test_fetch_etf_ohlcv_returns_dataframe():
    with patch('app.services.fetcher.ak.fund_etf_hist_sina', return_value=_fake_df()):
        result = fetch_etf_ohlcv('512480')
    assert result is not None
    assert list(result.columns) == ['date', 'open', 'high', 'low', 'close', 'volume']
    assert len(result) == 2


def test_fetch_etf_ohlcv_returns_none_on_all_failures():
    with patch('app.services.fetcher.ak.fund_etf_hist_sina', side_effect=Exception("network error")):
        result = fetch_etf_ohlcv('999999')
    assert result is None


def test_fetch_etf_holding_returns_none_on_error():
    with patch('app.services.fetcher.ak.fund_open_fund_info_em', side_effect=Exception("no data")):
        result = fetch_etf_holding('512480')
    assert result is None
```

- [ ] **Step 2: 运行测试确认失败**

```bash
pytest tests/test_fetcher.py -v
```
Expected: `ModuleNotFoundError`

- [ ] **Step 3: 写 `app/services/fetcher.py`**

```python
import warnings
import pandas as pd
import akshare as ak

warnings.filterwarnings('ignore')

START_DATE = '2021-01-01'


def fetch_etf_ohlcv(symbol: str) -> pd.DataFrame | None:
    """
    Fetch full OHLCV history for an ETF via akshare.
    Tries sz prefix first, then sh. Returns None if both fail.
    """
    prefix = f"sz{symbol}" if symbol[0] in ('0', '1', '3') else f"sh{symbol}"
    alt_prefix = f"sh{symbol}" if prefix.startswith('sz') else f"sz{symbol}"

    df = None
    for pfx in (prefix, alt_prefix):
        try:
            df = ak.fund_etf_hist_sina(symbol=pfx)
            break
        except Exception:
            continue

    if df is None:
        return None

    df['date'] = pd.to_datetime(df['date'])
    df = df[df['date'] >= START_DATE].copy()
    df.sort_values('date', inplace=True)
    df.reset_index(drop=True, inplace=True)
    df['date'] = df['date'].dt.strftime('%Y-%m-%d')

    required = {'date', 'close', 'volume'}
    if not required.issubset(df.columns):
        return None

    for col in ('open', 'high', 'low'):
        if col not in df.columns:
            df[col] = df['close']

    return df[['date', 'open', 'high', 'low', 'close', 'volume']]


def fetch_etf_holding(symbol: str) -> dict | None:
    """
    Fetch quarterly institutional holding ratio.
    Returns dict with keys: period, inst_ratio, retail_ratio.
    Returns None if unavailable.
    """
    try:
        df = ak.fund_open_fund_info_em(fund=symbol, indicator="持有人结构")
        if df is None or len(df) == 0:
            return None
        row = df.iloc[-1]
        period = str(row.get('报告期', ''))
        inst = row.get('机构投资者持有比例', None)
        retail = row.get('个人投资者持有比例', None)
        if inst is None:
            return None

        def pct(v):
            if isinstance(v, str):
                return float(v.rstrip('%'))
            return float(v)

        inst_ratio = pct(inst)
        retail_ratio = pct(retail) if retail is not None else round(100 - inst_ratio, 2)
        return {'period': period, 'inst_ratio': inst_ratio, 'retail_ratio': retail_ratio}
    except Exception:
        return None
```

- [ ] **Step 4: 运行测试确认通过**

```bash
pytest tests/test_fetcher.py -v
```
Expected: 3 PASSED

- [ ] **Step 5: 提交**

```bash
git add app/services/fetcher.py tests/test_fetcher.py
git commit -m "feat(etf-dashboard): add akshare fetcher service"
```

---

## Task 5: 热度评分服务

**Files:**
- Create: `quantitative/app/services/heat.py`
- Test: inline in `tests/test_database.py` (extend existing file)

- [ ] **Step 1: 在 `tests/test_database.py` 末尾追加测试**

```python
# 在 test_database.py 末尾追加：
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
```

- [ ] **Step 2: 运行测试确认失败**

```bash
pytest tests/test_database.py::test_compute_heat_scores_sets_ratio -v
```
Expected: `ImportError`

- [ ] **Step 3: 写 `app/services/heat.py`**

```python
import sqlite3


def compute_heat_scores(conn: sqlite3.Connection):
    """
    Recompute heat_score for every active ETF.
    heat_score = latest_volume / vol_ma20.  0.0 when vol_ma20 is unavailable.
    """
    c = conn.cursor()
    c.execute("SELECT symbol FROM etf_list WHERE active = 1")
    symbols = [row[0] for row in c.fetchall()]

    for symbol in symbols:
        c.execute("""
            SELECT d.volume, i.vol_ma20
            FROM etf_daily d
            JOIN etf_indicators i ON d.symbol = i.symbol AND d.date = i.date
            WHERE d.symbol = ?
            ORDER BY d.date DESC
            LIMIT 1
        """, (symbol,))
        row = c.fetchone()
        heat = (row[0] / row[1]) if (row and row[1] and row[1] > 0) else 0.0

        c.execute("""
            UPDATE etf_indicators SET heat_score = ?
            WHERE symbol = ? AND date = (
                SELECT MAX(date) FROM etf_indicators WHERE symbol = ?
            )
        """, (heat, symbol, symbol))

    conn.commit()
```

- [ ] **Step 4: 运行全部数据库测试确认通过**

```bash
pytest tests/test_database.py -v
```
Expected: 5 PASSED

- [ ] **Step 5: 提交**

```bash
git add app/services/heat.py tests/test_database.py
git commit -m "feat(etf-dashboard): add heat scoring service"
```

---

## Task 6: ETF CRUD 路由

**Files:**
- Create: `quantitative/app/routes/etf.py`
- Test: `quantitative/tests/test_routes_etf.py`

- [ ] **Step 1: 写 `tests/test_routes_etf.py`**

```python
from unittest.mock import patch
import pandas as pd


def _fake_df():
    dates = pd.date_range('2021-01-04', periods=60, freq='B').strftime('%Y-%m-%d').tolist()
    close = list(range(100, 160))
    return pd.DataFrame({
        'date': dates, 'open': [c - 0.5 for c in close],
        'high': [c + 0.5 for c in close], 'low': [c - 1.0 for c in close],
        'close': [float(c) for c in close], 'volume': [1_000_000.0] * 60,
    })


def test_list_etfs_empty(client):
    r = client.get("/api/etf")
    assert r.status_code == 200
    assert r.json() == []


def test_add_etf_success(client):
    with patch('app.routes.etf.fetch_etf_ohlcv', return_value=_fake_df()), \
         patch('app.routes.etf.fetch_etf_holding', return_value=None):
        r = client.post("/api/etf", json={"symbol": "512480", "name": "半导体ETF"})
    assert r.status_code == 201
    assert r.json()["symbol"] == "512480"


def test_add_etf_bad_symbol(client):
    with patch('app.routes.etf.fetch_etf_ohlcv', return_value=None):
        r = client.post("/api/etf", json={"symbol": "999999", "name": "假ETF"})
    assert r.status_code == 422


def test_list_etfs_after_add(client):
    with patch('app.routes.etf.fetch_etf_ohlcv', return_value=_fake_df()), \
         patch('app.routes.etf.fetch_etf_holding', return_value=None):
        client.post("/api/etf", json={"symbol": "512480", "name": "半导体ETF"})
    r = client.get("/api/etf")
    assert len(r.json()) == 1
    assert r.json()[0]["symbol"] == "512480"


def test_update_etf_name(client):
    with patch('app.routes.etf.fetch_etf_ohlcv', return_value=_fake_df()), \
         patch('app.routes.etf.fetch_etf_holding', return_value=None):
        client.post("/api/etf", json={"symbol": "512480", "name": "半导体ETF"})
    r = client.put("/api/etf/512480", json={"name": "半导体ETF(修改)"})
    assert r.status_code == 200


def test_delete_etf_soft_deletes(client):
    with patch('app.routes.etf.fetch_etf_ohlcv', return_value=_fake_df()), \
         patch('app.routes.etf.fetch_etf_holding', return_value=None):
        client.post("/api/etf", json={"symbol": "512480", "name": "半导体ETF"})
    r = client.delete("/api/etf/512480")
    assert r.status_code == 200
    etfs = client.get("/api/etf").json()
    active = [e for e in etfs if e['active'] == 1]
    assert len(active) == 0


def test_delete_nonexistent_returns_404(client):
    r = client.delete("/api/etf/000000")
    assert r.status_code == 404
```

- [ ] **Step 2: 运行测试确认失败**

```bash
pytest tests/test_routes_etf.py -v
```
Expected: `ModuleNotFoundError` or connection errors (no app yet)

- [ ] **Step 3: 写 `app/routes/etf.py`**

```python
from datetime import datetime
from fastapi import APIRouter, HTTPException
from pydantic import BaseModel
from app.database import get_conn, upsert_daily, upsert_indicators, upsert_holding
from app.services.fetcher import fetch_etf_ohlcv, fetch_etf_holding
from app.services.indicators import calculate_indicators

router = APIRouter()


class ETFCreate(BaseModel):
    symbol: str
    name: str


class ETFUpdate(BaseModel):
    name: str


@router.get("/etf")
def list_etfs():
    conn = get_conn()
    try:
        c = conn.cursor()
        c.execute("""
            SELECT e.symbol, e.name, e.active, e.created_at,
                   MAX(d.date) AS last_date
            FROM etf_list e
            LEFT JOIN etf_daily d ON e.symbol = d.symbol
            GROUP BY e.symbol
            ORDER BY e.created_at DESC
        """)
        return [dict(r) for r in c.fetchall()]
    finally:
        conn.close()


@router.post("/etf", status_code=201)
def add_etf(body: ETFCreate):
    conn = get_conn()
    try:
        c = conn.cursor()
        c.execute("SELECT symbol, active FROM etf_list WHERE symbol=?", (body.symbol,))
        existing = c.fetchone()
        if existing:
            conn.execute("UPDATE etf_list SET active=1, name=? WHERE symbol=?",
                         (body.name, body.symbol))
            conn.commit()
        else:
            conn.execute(
                "INSERT INTO etf_list (symbol, name, active, created_at) VALUES (?,?,1,?)",
                (body.symbol, body.name, datetime.now().strftime('%Y-%m-%d %H:%M:%S'))
            )
            conn.commit()

        df = fetch_etf_ohlcv(body.symbol)
        if df is None or len(df) < 30:
            raise HTTPException(status_code=422,
                                detail=f"无法获取 {body.symbol} 的数据，请检查代码是否正确")

        upsert_daily(conn, body.symbol, df)
        df_ind = calculate_indicators(df)
        upsert_indicators(conn, body.symbol, df_ind)

        holding = fetch_etf_holding(body.symbol)
        if holding:
            upsert_holding(conn, body.symbol, holding['period'],
                           holding['inst_ratio'], holding['retail_ratio'])

        return {"symbol": body.symbol, "name": body.name, "status": "ok"}
    finally:
        conn.close()


@router.put("/etf/{symbol}")
def update_etf(symbol: str, body: ETFUpdate):
    conn = get_conn()
    try:
        c = conn.cursor()
        c.execute("SELECT symbol FROM etf_list WHERE symbol=?", (symbol,))
        if not c.fetchone():
            raise HTTPException(status_code=404, detail=f"ETF {symbol} 不存在")
        conn.execute("UPDATE etf_list SET name=? WHERE symbol=?", (body.name, symbol))
        conn.commit()
        return {"status": "ok"}
    finally:
        conn.close()


@router.delete("/etf/{symbol}")
def delete_etf(symbol: str):
    conn = get_conn()
    try:
        c = conn.cursor()
        c.execute("SELECT symbol FROM etf_list WHERE symbol=?", (symbol,))
        if not c.fetchone():
            raise HTTPException(status_code=404, detail=f"ETF {symbol} 不存在")
        conn.execute("UPDATE etf_list SET active=0 WHERE symbol=?", (symbol,))
        conn.commit()
        return {"status": "ok"}
    finally:
        conn.close()
```

- [ ] **Step 4: 写 `app/main.py`（最小版本，只注册 etf 路由）**

```python
from fastapi import FastAPI
from fastapi.staticfiles import StaticFiles
from fastapi.responses import FileResponse
import os
from app.database import init_db
from app.routes import etf

app = FastAPI(title="ETF分析平台")
init_db()
app.include_router(etf.router, prefix="/api")

FRONTEND = os.path.join(os.path.dirname(__file__), '..', 'frontend')
if os.path.isdir(FRONTEND):
    app.mount("/static", StaticFiles(directory=FRONTEND), name="static")

    @app.get("/")
    def index():
        return FileResponse(os.path.join(FRONTEND, 'index.html'))
```

- [ ] **Step 5: 运行 CRUD 测试确认通过**

```bash
pytest tests/test_routes_etf.py -v
```
Expected: 7 PASSED

- [ ] **Step 6: 提交**

```bash
git add app/routes/etf.py app/main.py tests/test_routes_etf.py
git commit -m "feat(etf-dashboard): add ETF CRUD routes and FastAPI entry point"
```

---

## Task 7: 数据查询 & 热度排行路由

**Files:**
- Create: `quantitative/app/routes/analysis.py`
- Test: `quantitative/tests/test_routes_analysis.py`

- [ ] **Step 1: 写 `tests/test_routes_analysis.py`**

```python
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
    compute_heat_scores(db_module.get_conn())
    r = client.get("/api/heat/rank")
    assert r.status_code == 200
    data = r.json()
    assert len(data) >= 1
    assert 'heat_score' in data[0]
```

- [ ] **Step 2: 运行测试确认失败**

```bash
pytest tests/test_routes_analysis.py -v
```
Expected: 404 errors (routes not registered yet)

- [ ] **Step 3: 写 `app/routes/analysis.py`**

```python
from fastapi import APIRouter, HTTPException
from app.database import get_conn

router = APIRouter()


@router.get("/etf/{symbol}/data")
def get_etf_data(symbol: str, start: str = None, end: str = None):
    conn = get_conn()
    try:
        c = conn.cursor()
        c.execute("SELECT symbol FROM etf_list WHERE symbol=?", (symbol,))
        if not c.fetchone():
            raise HTTPException(status_code=404, detail=f"ETF {symbol} 不存在")

        sql = """
            SELECT d.date, d.open, d.high, d.low, d.close, d.volume,
                   i.ma20, i.w_ma20, i.m_ma12, i.vol_ma20,
                   i.macd, i.macd_signal, i.macd_hist,
                   i.heat_score, i.buy_signal, i.sell_signal
            FROM etf_daily d
            LEFT JOIN etf_indicators i ON d.symbol=i.symbol AND d.date=i.date
            WHERE d.symbol=?
        """
        params = [symbol]
        if start:
            sql += " AND d.date >= ?"
            params.append(start)
        if end:
            sql += " AND d.date <= ?"
            params.append(end)
        sql += " ORDER BY d.date ASC"

        c.execute(sql, params)
        rows = [dict(r) for r in c.fetchall()]

        c.execute("""
            SELECT period, inst_ratio, retail_ratio FROM etf_holding
            WHERE symbol=? ORDER BY period DESC LIMIT 1
        """, (symbol,))
        holding_row = c.fetchone()

        return {"data": rows, "holding": dict(holding_row) if holding_row else None}
    finally:
        conn.close()


@router.get("/heat/rank")
def heat_rank():
    conn = get_conn()
    try:
        c = conn.cursor()
        c.execute("""
            SELECT e.symbol, e.name,
                   i.heat_score, i.buy_signal, i.sell_signal,
                   d.close,
                   ROUND((d.close - d2.close) / d2.close * 100, 2) AS change_pct
            FROM etf_list e
            JOIN etf_indicators i ON e.symbol = i.symbol
            JOIN etf_daily d      ON e.symbol = d.symbol AND i.date = d.date
            LEFT JOIN etf_daily d2 ON e.symbol = d2.symbol
                AND d2.date = (
                    SELECT date FROM etf_daily
                    WHERE symbol = e.symbol AND date < i.date
                    ORDER BY date DESC LIMIT 1
                )
            WHERE e.active = 1
              AND i.date = (SELECT MAX(date) FROM etf_indicators WHERE symbol = e.symbol)
            ORDER BY i.heat_score DESC
        """)
        return [dict(r) for r in c.fetchall()]
    finally:
        conn.close()
```

- [ ] **Step 4: 在 `app/main.py` 注册新路由**

```python
# app/main.py — 替换全文
from fastapi import FastAPI
from fastapi.staticfiles import StaticFiles
from fastapi.responses import FileResponse
import os
from app.database import init_db
from app.routes import etf, analysis

app = FastAPI(title="ETF分析平台")
init_db()
app.include_router(etf.router, prefix="/api")
app.include_router(analysis.router, prefix="/api")

FRONTEND = os.path.join(os.path.dirname(__file__), '..', 'frontend')
if os.path.isdir(FRONTEND):
    app.mount("/static", StaticFiles(directory=FRONTEND), name="static")

    @app.get("/")
    def index():
        return FileResponse(os.path.join(FRONTEND, 'index.html'))
```

- [ ] **Step 5: 运行分析路由测试确认通过**

```bash
pytest tests/test_routes_analysis.py -v
```
Expected: 4 PASSED

- [ ] **Step 6: 提交**

```bash
git add app/routes/analysis.py app/main.py tests/test_routes_analysis.py
git commit -m "feat(etf-dashboard): add data query and heat rank routes"
```

---

## Task 8: 数据更新路由 & 完整 main.py

**Files:**
- Create: `quantitative/app/routes/update.py`
- Modify: `quantitative/app/main.py`
- Test: `quantitative/tests/test_routes_update.py`

- [ ] **Step 1: 写 `tests/test_routes_update.py`**

```python
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
```

- [ ] **Step 2: 运行测试确认失败**

```bash
pytest tests/test_routes_update.py -v
```
Expected: errors (route not registered)

- [ ] **Step 3: 写 `app/routes/update.py`**

```python
import time
import pandas as pd
from datetime import datetime
from fastapi import APIRouter
from app.database import get_conn, upsert_daily, upsert_indicators
from app.services.fetcher import fetch_etf_ohlcv
from app.services.indicators import calculate_indicators
from app.services.heat import compute_heat_scores

router = APIRouter()

_state = {"status": "idle", "time": None, "updated": 0, "failed": 0, "elapsed": 0}


@router.get("/update/status")
def get_status():
    return _state


@router.post("/update")
def trigger_update():
    conn = get_conn()
    try:
        c = conn.cursor()
        c.execute("SELECT symbol FROM etf_list WHERE active=1")
        symbols = [row[0] for row in c.fetchall()]

        updated, failed, results = 0, 0, []
        t0 = time.time()

        for symbol in symbols:
            try:
                c.execute("SELECT MAX(date) FROM etf_daily WHERE symbol=?", (symbol,))
                last_date = c.fetchone()[0]

                df_new = fetch_etf_ohlcv(symbol)
                if df_new is None:
                    failed += 1
                    results.append({"symbol": symbol, "status": "failed",
                                    "reason": "akshare获取失败"})
                    continue

                if last_date:
                    df_new = df_new[df_new['date'] > last_date]

                if len(df_new) > 0:
                    upsert_daily(conn, symbol, df_new)

                # Recalculate indicators from full history
                c.execute(
                    "SELECT date, open, high, low, close, volume FROM etf_daily "
                    "WHERE symbol=? ORDER BY date ASC", (symbol,)
                )
                full_df = pd.DataFrame([dict(r) for r in c.fetchall()])
                df_ind = calculate_indicators(full_df)
                upsert_indicators(conn, symbol, df_ind)

                updated += 1
                results.append({"symbol": symbol, "status": "ok"})
            except Exception as e:
                failed += 1
                results.append({"symbol": symbol, "status": "failed", "reason": str(e)})

        compute_heat_scores(conn)
        elapsed = round(time.time() - t0, 1)

        _state.update({
            "status": "done",
            "time": datetime.now().strftime('%Y-%m-%d %H:%M:%S'),
            "updated": updated,
            "failed": failed,
            "elapsed": elapsed,
        })
        return {**_state, "results": results}
    finally:
        conn.close()
```

- [ ] **Step 4: 完善 `app/main.py`（注册全部路由）**

```python
from fastapi import FastAPI
from fastapi.staticfiles import StaticFiles
from fastapi.responses import FileResponse
import os
from app.database import init_db
from app.routes import etf, analysis, update

app = FastAPI(title="ETF分析平台")
init_db()
app.include_router(etf.router, prefix="/api")
app.include_router(analysis.router, prefix="/api")
app.include_router(update.router, prefix="/api")

FRONTEND = os.path.join(os.path.dirname(__file__), '..', 'frontend')
if os.path.isdir(FRONTEND):
    app.mount("/static", StaticFiles(directory=FRONTEND), name="static")

    @app.get("/")
    def index():
        return FileResponse(os.path.join(FRONTEND, 'index.html'))
```

- [ ] **Step 5: 运行所有后端测试确认全部通过**

```bash
pytest tests/ -v
```
Expected: 所有测试 PASSED（不含 test_fetcher.py 中需要网络的测试）

- [ ] **Step 6: 手动验证后端启动**

```bash
python -m uvicorn app.main:app --reload --port 8000
# 浏览器访问 http://localhost:8000/api/etf → 应返回 []
# 访问 http://localhost:8000/api/update/status → {"status":"idle",...}
```

- [ ] **Step 7: 提交**

```bash
git add app/routes/update.py app/main.py tests/test_routes_update.py
git commit -m "feat(etf-dashboard): add update route and complete backend"
```

---

## Task 9: 前端基础（CSS + HTML 骨架）

**Files:**
- Create: `quantitative/frontend/css/style.css`
- Create: `quantitative/frontend/index.html`

- [ ] **Step 1: 写 `frontend/css/style.css`**

```css
:root {
  --bg: #0d0d1a;
  --bg2: #12121e;
  --bg3: #1a1a2e;
  --border: #2a2a3e;
  --text: #e0e0f0;
  --text2: #8888aa;
  --accent: #7c9eff;
  --green: #4caf50;
  --red: #f44336;
  --orange: #ff8c00;
  --yellow: #ffc107;
}
* { box-sizing: border-box; margin: 0; padding: 0; }
body { background: var(--bg); color: var(--text); font-family: 'Segoe UI', sans-serif; font-size: 14px; }

/* Nav */
.nav { display: flex; align-items: center; background: var(--bg2); border-bottom: 1px solid var(--border);
       padding: 0 20px; height: 48px; gap: 4px; }
.nav-title { color: var(--accent); font-weight: 700; margin-right: 24px; font-size: 16px; }
.tab-btn { background: none; border: none; color: var(--text2); cursor: pointer; padding: 12px 16px;
           font-size: 14px; border-bottom: 2px solid transparent; transition: all 0.2s; }
.tab-btn:hover { color: var(--text); }
.tab-btn.active { color: var(--accent); border-bottom-color: var(--accent); }
.nav-status { margin-left: auto; color: var(--text2); font-size: 12px; }

/* Tab panels */
.tab-panel { display: none; height: calc(100vh - 48px); overflow: hidden; }
.tab-panel.active { display: flex; }

/* Tab 1 — Chart Analysis */
.heat-sidebar { width: 200px; background: var(--bg2); border-right: 1px solid var(--border);
                overflow-y: auto; padding: 12px 0; flex-shrink: 0; }
.heat-sidebar-title { padding: 0 12px 8px; color: var(--text2); font-size: 11px;
                       text-transform: uppercase; letter-spacing: 1px; }
.heat-item { padding: 8px 12px; cursor: pointer; display: flex; justify-content: space-between;
             align-items: center; border-left: 3px solid transparent; }
.heat-item:hover { background: var(--bg3); }
.heat-item.selected { background: var(--bg3); border-left-color: var(--accent); }
.heat-item-name { font-size: 13px; }
.heat-badge { font-size: 12px; }

.chart-area { flex: 1; overflow-y: auto; padding: 16px; display: flex; flex-direction: column; gap: 12px; }
.chart-toolbar { display: flex; align-items: center; gap: 8px; flex-wrap: wrap; }
.chart-toolbar h3 { color: var(--accent); margin-right: 8px; }
.range-btn { background: var(--bg3); border: 1px solid var(--border); color: var(--text2);
             padding: 4px 12px; border-radius: 4px; cursor: pointer; font-size: 12px; }
.range-btn.active, .range-btn:hover { background: var(--accent); color: #fff; border-color: var(--accent); }
.chart-box { background: var(--bg2); border: 1px solid var(--border); border-radius: 6px; padding: 12px; }
.chart-box canvas { max-height: 220px; }
.info-row { display: flex; gap: 12px; flex-wrap: wrap; }
.info-card { background: var(--bg3); border-radius: 6px; padding: 12px; flex: 1; min-width: 140px; }
.info-card .label { font-size: 11px; color: var(--text2); text-transform: uppercase; margin-bottom: 4px; }
.info-card .value { font-size: 20px; font-weight: 700; }
.signal-buy { color: var(--red); }
.signal-sell { color: var(--green); }
.signal-watch { color: var(--yellow); }

/* Tab 2 — ETF Management */
.mgmt-panel { flex: 1; padding: 20px; overflow-y: auto; }
.add-form { display: flex; gap: 8px; margin-bottom: 20px; align-items: center; }
.add-form input { background: var(--bg3); border: 1px solid var(--border); color: var(--text);
                  padding: 8px 12px; border-radius: 4px; font-size: 14px; }
.btn { background: var(--accent); border: none; color: #fff; padding: 8px 16px;
       border-radius: 4px; cursor: pointer; font-size: 13px; }
.btn:hover { opacity: 0.85; }
.btn-danger { background: var(--red); }
.btn-sm { padding: 4px 10px; font-size: 12px; }

/* Tables */
table { width: 100%; border-collapse: collapse; }
th { text-align: left; padding: 8px 12px; color: var(--text2); font-size: 12px;
     text-transform: uppercase; border-bottom: 1px solid var(--border); }
td { padding: 10px 12px; border-bottom: 1px solid var(--border); font-size: 13px; }
tr:hover td { background: var(--bg3); }
.badge-active { color: var(--green); }
.badge-inactive { color: var(--text2); }

/* Tab 3 — Heat Rank */
.rank-panel { flex: 1; padding: 20px; overflow-y: auto; }
.heat-3 { color: var(--red); }
.heat-2 { color: var(--orange); }
.heat-1 { color: var(--yellow); }
.heat-0 { color: var(--text2); }

/* Tab 4 — Update */
.update-panel { flex: 1; padding: 20px; overflow-y: auto; }
.update-header { display: flex; align-items: center; gap: 16px; margin-bottom: 20px; }
.update-log { background: var(--bg3); border-radius: 6px; padding: 12px;
              max-height: 400px; overflow-y: auto; font-family: monospace; font-size: 12px; }
.log-ok { color: var(--green); }
.log-fail { color: var(--red); }

/* Toast */
#toast { position: fixed; bottom: 20px; right: 20px; padding: 10px 18px;
         border-radius: 6px; background: var(--bg3); border: 1px solid var(--border);
         color: var(--text); display: none; z-index: 1000; font-size: 13px; }
#toast.show { display: block; }
#toast.error { border-color: var(--red); color: var(--red); }
```

- [ ] **Step 2: 写 `frontend/index.html`（完整骨架）**

```html
<!DOCTYPE html>
<html lang="zh">
<head>
  <meta charset="UTF-8">
  <title>ETF 分析平台</title>
  <link rel="stylesheet" href="/static/css/style.css">
  <script src="https://cdn.jsdelivr.net/npm/chart.js@4.4.0/dist/chart.umd.min.js"></script>
  <script src="https://cdn.jsdelivr.net/npm/chartjs-adapter-date-fns@3.0.0/dist/chartjs-adapter-date-fns.bundle.min.js"></script>
  <script src="https://cdn.jsdelivr.net/npm/chartjs-chart-financial@0.1.1/dist/chartjs-chart-financial.min.js"></script>
</head>
<body>

<nav class="nav">
  <span class="nav-title">📊 ETF 分析平台</span>
  <button class="tab-btn active" data-tab="chart">图表分析</button>
  <button class="tab-btn" data-tab="manage">ETF 管理</button>
  <button class="tab-btn" data-tab="rank">热度排行</button>
  <button class="tab-btn" data-tab="update">数据更新</button>
  <span class="nav-status" id="nav-status">—</span>
</nav>

<!-- Tab 1: 图表分析 -->
<div class="tab-panel active" id="tab-chart">
  <div class="heat-sidebar">
    <div class="heat-sidebar-title">🔥 热度榜</div>
    <div id="heat-list"></div>
  </div>
  <div class="chart-area">
    <div class="chart-toolbar">
      <h3 id="chart-title">请选择 ETF</h3>
      <button class="range-btn active" data-range="3M">3M</button>
      <button class="range-btn" data-range="1M">1M</button>
      <button class="range-btn" data-range="6M">6M</button>
      <button class="range-btn" data-range="1Y">1Y</button>
    </div>
    <div class="chart-box"><canvas id="chart-price"></canvas></div>
    <div class="chart-box"><canvas id="chart-macd"></canvas></div>
    <div class="chart-box"><canvas id="chart-volume"></canvas></div>
    <div class="info-row">
      <div class="info-card">
        <div class="label">当前信号</div>
        <div class="value" id="info-signal">—</div>
      </div>
      <div class="info-card">
        <div class="label">热度得分</div>
        <div class="value" id="info-heat">—</div>
      </div>
      <div class="info-card" id="info-holding-card">
        <div class="label">机构持仓</div>
        <div class="value" id="info-holding">—</div>
        <div style="font-size:11px;color:var(--text2);margin-top:4px" id="info-holding-period"></div>
      </div>
    </div>
  </div>
</div>

<!-- Tab 2: ETF 管理 -->
<div class="tab-panel" id="tab-manage">
  <div class="mgmt-panel">
    <div class="add-form">
      <input id="add-symbol" placeholder="ETF 代码（如 512480）" style="width:180px">
      <input id="add-name" placeholder="名称（如 半导体ETF）" style="width:180px">
      <button class="btn" id="btn-add">+ 添加</button>
    </div>
    <table id="manage-table">
      <thead>
        <tr><th>代码</th><th>名称</th><th>状态</th><th>最后更新</th><th>操作</th></tr>
      </thead>
      <tbody id="manage-tbody"></tbody>
    </table>
  </div>
</div>

<!-- Tab 3: 热度排行 -->
<div class="tab-panel" id="tab-rank">
  <div class="rank-panel">
    <table id="rank-table">
      <thead>
        <tr><th>#</th><th>名称</th><th>热度</th><th>信号</th><th>最新价</th><th>涨跌幅</th></tr>
      </thead>
      <tbody id="rank-tbody"></tbody>
    </table>
  </div>
</div>

<!-- Tab 4: 数据更新 -->
<div class="tab-panel" id="tab-update">
  <div class="update-panel">
    <div class="update-header">
      <button class="btn" id="btn-update">🔄 更新数据</button>
      <span id="update-status-text" style="color:var(--text2)">—</span>
    </div>
    <div class="update-log" id="update-log"></div>
  </div>
</div>

<div id="toast"></div>

<script src="/static/js/api.js"></script>
<script src="/static/js/charts.js"></script>
<script src="/static/js/app.js"></script>
</body>
</html>
```

- [ ] **Step 3: 启动后端，访问首页验证 HTML 结构加载正常**

```bash
python -m uvicorn app.main:app --port 8000
# 浏览器访问 http://localhost:8000 → 应显示导航栏和 4 个 Tab 按钮
```

- [ ] **Step 4: 提交**

```bash
git add frontend/css/style.css frontend/index.html
git commit -m "feat(etf-dashboard): add frontend HTML skeleton and dark theme CSS"
```

---

## Task 10: 前端 API 层

**Files:**
- Create: `quantitative/frontend/js/api.js`

- [ ] **Step 1: 写 `frontend/js/api.js`**

```js
const API = {
  async _fetch(url, opts = {}) {
    const r = await fetch(url, opts);
    if (!r.ok) {
      const err = await r.json().catch(() => ({ detail: r.statusText }));
      throw new Error(err.detail || r.statusText);
    }
    return r.json();
  },

  getEtfList()                { return this._fetch('/api/etf'); },
  addEtf(symbol, name)        { return this._fetch('/api/etf', {
    method: 'POST', headers: {'Content-Type':'application/json'},
    body: JSON.stringify({ symbol, name }) }); },
  updateEtf(symbol, name)     { return this._fetch(`/api/etf/${symbol}`, {
    method: 'PUT', headers: {'Content-Type':'application/json'},
    body: JSON.stringify({ name }) }); },
  deleteEtf(symbol)           { return this._fetch(`/api/etf/${symbol}`, { method: 'DELETE' }); },

  getEtfData(symbol, start, end) {
    const p = new URLSearchParams();
    if (start) p.set('start', start);
    if (end)   p.set('end', end);
    return this._fetch(`/api/etf/${symbol}/data?${p}`);
  },

  getHeatRank()               { return this._fetch('/api/heat/rank'); },
  triggerUpdate()             { return this._fetch('/api/update', { method: 'POST' }); },
  getUpdateStatus()           { return this._fetch('/api/update/status'); },
};
```

- [ ] **Step 2: 在浏览器控制台测试 API 层**

```
// 打开 http://localhost:8000 的 DevTools 控制台，运行：
API.getEtfList().then(console.log)
// 应返回 []
API.getUpdateStatus().then(console.log)
// 应返回 {status: "idle", ...}
```

- [ ] **Step 3: 提交**

```bash
git add frontend/js/api.js
git commit -m "feat(etf-dashboard): add frontend API fetch wrapper"
```

---

## Task 11: 前端图表模块

**Files:**
- Create: `quantitative/frontend/js/charts.js`

- [ ] **Step 1: 写 `frontend/js/charts.js`**

```js
let priceChart = null, macdChart = null, volChart = null;

function destroyCharts() {
  [priceChart, macdChart, volChart].forEach(c => c && c.destroy());
  priceChart = macdChart = volChart = null;
}

function renderCharts(data) {
  destroyCharts();

  const labels = data.map(d => d.date);

  // ── 价格 K 线 + MA 线 ──────────────────────────────────────────────
  const ohlc = data.map(d => ({
    x: new Date(d.date).getTime(),
    o: d.open, h: d.high, l: d.low, c: d.close
  }));
  const maLine   = data.map(d => ({ x: new Date(d.date).getTime(), y: d.ma20 }));
  const wMaLine  = data.map(d => ({ x: new Date(d.date).getTime(), y: d.w_ma20 }));
  const mMaLine  = data.map(d => ({ x: new Date(d.date).getTime(), y: d.m_ma12 }));

  const commonTimeAxis = {
    type: 'time', time: { unit: 'month', displayFormats: { month: 'yy/MM' } },
    grid: { color: '#1e1e30' }, ticks: { color: '#666' }
  };

  priceChart = new Chart(document.getElementById('chart-price'), {
    data: {
      datasets: [
        { type: 'candlestick', label: '价格', data: ohlc,
          color: { up: '#ef5350', down: '#26a69a', unchanged: '#999' } },
        { type: 'line', label: 'MA20', data: maLine,
          borderColor: '#ffa726', borderWidth: 1, pointRadius: 0, tension: 0.3 },
        { type: 'line', label: 'W_MA20', data: wMaLine,
          borderColor: '#7c9eff', borderWidth: 1.5, pointRadius: 0, tension: 0.3 },
        { type: 'line', label: 'M_MA12', data: mMaLine,
          borderColor: '#ce93d8', borderWidth: 1.5, pointRadius: 0,
          borderDash: [4, 2], tension: 0.3 },
      ]
    },
    options: {
      animation: false,
      plugins: { legend: { labels: { color: '#888', boxWidth: 12, font: { size: 11 } } } },
      scales: { x: commonTimeAxis, y: { grid: { color: '#1e1e30' }, ticks: { color: '#666' } } }
    }
  });

  // ── MACD ──────────────────────────────────────────────────────────
  const histColors = data.map(d =>
    (d.macd_hist || 0) >= 0 ? 'rgba(239,83,80,0.6)' : 'rgba(38,166,154,0.6)'
  );
  macdChart = new Chart(document.getElementById('chart-macd'), {
    data: {
      datasets: [
        { type: 'bar', label: 'MACD柱', data: data.map(d => ({ x: new Date(d.date).getTime(), y: d.macd_hist })),
          backgroundColor: histColors },
        { type: 'line', label: 'MACD', data: data.map(d => ({ x: new Date(d.date).getTime(), y: d.macd })),
          borderColor: '#7c9eff', borderWidth: 1.5, pointRadius: 0 },
        { type: 'line', label: 'Signal', data: data.map(d => ({ x: new Date(d.date).getTime(), y: d.macd_signal })),
          borderColor: '#ffa726', borderWidth: 1.5, pointRadius: 0 },
      ]
    },
    options: {
      animation: false,
      plugins: { legend: { labels: { color: '#888', boxWidth: 12, font: { size: 11 } } } },
      scales: { x: commonTimeAxis, y: { grid: { color: '#1e1e30' }, ticks: { color: '#666' } } }
    }
  });

  // ── 成交量 ──────────────────────────────────────────────────────────
  const volColors = data.map((d, i) => {
    const prev = data[i - 1];
    return (!prev || d.close >= prev.close) ? 'rgba(239,83,80,0.6)' : 'rgba(38,166,154,0.6)';
  });
  volChart = new Chart(document.getElementById('chart-volume'), {
    data: {
      datasets: [
        { type: 'bar', label: '成交量', data: data.map(d => ({ x: new Date(d.date).getTime(), y: d.volume })),
          backgroundColor: volColors },
        { type: 'line', label: 'Vol MA20', data: data.map(d => ({ x: new Date(d.date).getTime(), y: d.vol_ma20 })),
          borderColor: '#ffa726', borderWidth: 1.5, pointRadius: 0 },
      ]
    },
    options: {
      animation: false,
      plugins: { legend: { labels: { color: '#888', boxWidth: 12, font: { size: 11 } } } },
      scales: { x: commonTimeAxis, y: { grid: { color: '#1e1e30' }, ticks: { color: '#666', callback: v => (v / 1e6).toFixed(0) + 'M' } } }
    }
  });
}
```

- [ ] **Step 2: 提交**

```bash
git add frontend/js/charts.js
git commit -m "feat(etf-dashboard): add Chart.js chart module (candlestick/MACD/volume)"
```

---

## Task 12: 前端应用逻辑 & 集成测试

**Files:**
- Create: `quantitative/frontend/js/app.js`

- [ ] **Step 1: 写 `frontend/js/app.js`**

```js
// ── 工具 ─────────────────────────────────────────────────────────────
function showToast(msg, isError = false) {
  const t = document.getElementById('toast');
  t.textContent = msg;
  t.className = 'show' + (isError ? ' error' : '');
  setTimeout(() => t.className = '', 3000);
}

function dateRangeFor(range) {
  const end = new Date();
  const start = new Date();
  const map = { '1M': 1, '3M': 3, '6M': 6, '1Y': 12 };
  start.setMonth(start.getMonth() - (map[range] || 3));
  return {
    start: start.toISOString().slice(0, 10),
    end: end.toISOString().slice(0, 10),
  };
}

function heatBadge(score) {
  if (score >= 2)   return { text: '🔥🔥🔥', cls: 'heat-3' };
  if (score >= 1.5) return { text: '🔥🔥',   cls: 'heat-2' };
  if (score >= 1.2) return { text: '🔥',     cls: 'heat-1' };
  return { text: '—', cls: 'heat-0' };
}

function signalText(buy, sell) {
  if (buy)  return { text: '买入 ▲', cls: 'signal-buy' };
  if (sell) return { text: '卖出 ▼', cls: 'signal-sell' };
  return { text: '观察 —', cls: 'signal-watch' };
}

// ── Tab 切换 ─────────────────────────────────────────────────────────
document.querySelectorAll('.tab-btn').forEach(btn => {
  btn.addEventListener('click', () => {
    const tab = btn.dataset.tab;
    document.querySelectorAll('.tab-btn').forEach(b => b.classList.remove('active'));
    document.querySelectorAll('.tab-panel').forEach(p => p.classList.remove('active'));
    btn.classList.add('active');
    document.getElementById('tab-' + tab).classList.add('active');
    if (tab === 'rank')   loadRank();
    if (tab === 'manage') loadManage();
    if (tab === 'update') loadUpdateStatus();
  });
});

// ── 状态 ─────────────────────────────────────────────────────────────
let selectedSymbol = null;
let selectedRange = '3M';

// ── 热度侧边栏 ───────────────────────────────────────────────────────
async function loadHeatList() {
  try {
    const rank = await API.getHeatRank();
    const list = document.getElementById('heat-list');
    list.innerHTML = rank.map(r => {
      const b = heatBadge(r.heat_score || 0);
      return `<div class="heat-item${r.symbol === selectedSymbol ? ' selected' : ''}"
                   data-symbol="${r.symbol}" data-name="${r.name}">
                <span class="heat-item-name">${r.name}</span>
                <span class="heat-badge ${b.cls}">${b.text}</span>
              </div>`;
    }).join('');
    list.querySelectorAll('.heat-item').forEach(el => {
      el.addEventListener('click', () => selectEtf(el.dataset.symbol, el.dataset.name));
    });
    // If nothing selected yet, auto-select first
    if (!selectedSymbol && rank.length > 0) selectEtf(rank[0].symbol, rank[0].name);
  } catch (e) {
    // Heat list empty — show manage prompt
    document.getElementById('heat-list').innerHTML =
      '<div style="padding:12px;color:var(--text2);font-size:12px">请先在 ETF 管理中添加 ETF</div>';
  }
}

// ── ETF 图表加载 ──────────────────────────────────────────────────────
async function selectEtf(symbol, name) {
  selectedSymbol = symbol;
  document.getElementById('chart-title').textContent = name + ' (' + symbol + ')';
  document.querySelectorAll('.heat-item').forEach(el => {
    el.classList.toggle('selected', el.dataset.symbol === symbol);
  });
  await loadChartData();
}

async function loadChartData() {
  if (!selectedSymbol) return;
  const { start, end } = dateRangeFor(selectedRange);
  try {
    const result = await API.getEtfData(selectedSymbol, start, end);
    const data = result.data;
    if (data.length === 0) { showToast('该时间范围无数据', true); return; }

    renderCharts(data);

    // Info cards
    const last = data[data.length - 1];
    const sig = signalText(last.buy_signal, last.sell_signal);
    document.getElementById('info-signal').textContent = sig.text;
    document.getElementById('info-signal').className = 'value ' + sig.cls;

    const heatScore = (last.heat_score || 0).toFixed(2);
    const hb = heatBadge(last.heat_score || 0);
    document.getElementById('info-heat').textContent = heatScore + 'x ' + hb.text;
    document.getElementById('info-heat').className = 'value ' + hb.cls;

    if (result.holding) {
      const h = result.holding;
      document.getElementById('info-holding').textContent = h.inst_ratio.toFixed(1) + '%';
      document.getElementById('info-holding-period').textContent = '截至 ' + h.period;
    } else {
      document.getElementById('info-holding').textContent = '暂无数据';
      document.getElementById('info-holding-period').textContent = '';
    }
  } catch (e) {
    showToast('加载图表失败: ' + e.message, true);
  }
}

// ── 时间区间按钮 ──────────────────────────────────────────────────────
document.querySelectorAll('.range-btn').forEach(btn => {
  btn.addEventListener('click', () => {
    document.querySelectorAll('.range-btn').forEach(b => b.classList.remove('active'));
    btn.classList.add('active');
    selectedRange = btn.dataset.range;
    loadChartData();
  });
});

// ── ETF 管理 Tab ──────────────────────────────────────────────────────
async function loadManage() {
  const etfs = await API.getEtfList().catch(() => []);
  const tbody = document.getElementById('manage-tbody');
  tbody.innerHTML = etfs.map(e => `
    <tr>
      <td>${e.symbol}</td>
      <td>${e.name}</td>
      <td class="${e.active ? 'badge-active' : 'badge-inactive'}">${e.active ? '启用' : '停用'}</td>
      <td>${e.last_date || '—'}</td>
      <td>
        <button class="btn btn-sm btn-danger" onclick="removeEtf('${e.symbol}')">删除</button>
      </td>
    </tr>`).join('');
}

document.getElementById('btn-add').addEventListener('click', async () => {
  const symbol = document.getElementById('add-symbol').value.trim();
  const name   = document.getElementById('add-name').value.trim();
  if (!symbol || !name) { showToast('请填写代码和名称', true); return; }
  try {
    document.getElementById('btn-add').textContent = '拉取中…';
    document.getElementById('btn-add').disabled = true;
    await API.addEtf(symbol, name);
    showToast('添加成功：' + name);
    document.getElementById('add-symbol').value = '';
    document.getElementById('add-name').value = '';
    loadManage();
    loadHeatList();
  } catch (e) {
    showToast('添加失败: ' + e.message, true);
  } finally {
    document.getElementById('btn-add').textContent = '+ 添加';
    document.getElementById('btn-add').disabled = false;
  }
});

async function removeEtf(symbol) {
  if (!confirm(`确认删除 ${symbol}？`)) return;
  try {
    await API.deleteEtf(symbol);
    showToast('已删除 ' + symbol);
    loadManage();
    loadHeatList();
  } catch (e) {
    showToast('删除失败: ' + e.message, true);
  }
}

// ── 热度排行 Tab ──────────────────────────────────────────────────────
async function loadRank() {
  const rank = await API.getHeatRank().catch(() => []);
  const tbody = document.getElementById('rank-tbody');
  tbody.innerHTML = rank.map((r, i) => {
    const b = heatBadge(r.heat_score || 0);
    const sig = signalText(r.buy_signal, r.sell_signal);
    const chgCls = (r.change_pct || 0) >= 0 ? 'signal-buy' : 'signal-sell';
    return `<tr>
      <td>${i + 1}</td>
      <td>${r.name}</td>
      <td class="${b.cls}">${(r.heat_score || 0).toFixed(2)}x ${b.text}</td>
      <td class="${sig.cls}">${sig.text}</td>
      <td>${r.close ? r.close.toFixed(3) : '—'}</td>
      <td class="${chgCls}">${r.change_pct != null ? r.change_pct.toFixed(2) + '%' : '—'}</td>
    </tr>`;
  }).join('');
}

// ── 数据更新 Tab ──────────────────────────────────────────────────────
async function loadUpdateStatus() {
  const s = await API.getUpdateStatus().catch(() => ({}));
  document.getElementById('update-status-text').textContent =
    s.time ? `上次更新: ${s.time}，耗时 ${s.elapsed}s，成功 ${s.updated}，失败 ${s.failed}` : '尚未更新';
}

document.getElementById('btn-update').addEventListener('click', async () => {
  const log = document.getElementById('update-log');
  const btn = document.getElementById('btn-update');
  log.innerHTML = '<span style="color:var(--text2)">更新中，请稍候…</span>';
  btn.disabled = true;
  try {
    const result = await API.triggerUpdate();
    log.innerHTML = (result.results || []).map(r =>
      `<div class="${r.status === 'ok' ? 'log-ok' : 'log-fail'}">` +
      `[${r.status === 'ok' ? '✓' : '✗'}] ${r.symbol}` +
      (r.reason ? ` — ${r.reason}` : '') + '</div>'
    ).join('');
    loadUpdateStatus();
    loadHeatList();
    showToast(`更新完成: ${result.updated} 成功, ${result.failed} 失败`);
  } catch (e) {
    log.innerHTML = `<span class="log-fail">更新失败: ${e.message}</span>`;
    showToast('更新失败: ' + e.message, true);
  } finally {
    btn.disabled = false;
  }
});

// ── 初始化 ────────────────────────────────────────────────────────────
(async function init() {
  const status = await API.getUpdateStatus().catch(() => ({}));
  document.getElementById('nav-status').textContent =
    status.time ? '上次更新: ' + status.time : '';
  await loadHeatList();
})();
```

- [ ] **Step 2: 提交**

```bash
git add frontend/js/app.js
git commit -m "feat(etf-dashboard): add frontend app logic (tabs, charts, CRUD, heat)"
```

- [ ] **Step 3: 端到端手动验证**

```bash
# 从 quantitative/ 目录启动
python -m uvicorn app.main:app --reload --port 8000
```

在浏览器依次验证：
1. 访问 `http://localhost:8000` → 页面正常显示，4 个 Tab 可点击
2. 点击 **ETF 管理** → 表格为空，输入 `510300` / `沪深300ETF` 点击添加 → 等待数据拉取完成（约 5-10 秒）
3. 切回 **图表分析** → 左侧热度榜出现沪深300ETF，右侧显示 K 线/MACD/成交量三图
4. 点击时间区间 **1Y** → 图表重绘为 1 年数据
5. 点击 **热度排行** → 表格显示热度分值和信号状态
6. 点击 **数据更新** → 点击「更新数据」按钮 → 日志显示更新结果
7. 再添加 1 只 ETF（如 `512480` 半导体ETF），确认热度榜和排行均刷新

- [ ] **Step 4: 运行全部测试最终确认**

```bash
pytest tests/ -v
```
Expected: 全部 PASSED

- [ ] **Step 5: 最终提交**

```bash
git add .
git commit -m "feat(etf-dashboard): complete ETF local analysis platform (backend + frontend)"
```

---

## 附：启动命令

```bash
cd quantitative
pip install -r requirements.txt
python -m uvicorn app.main:app --reload --port 8000
# 浏览器访问 http://localhost:8000
```
