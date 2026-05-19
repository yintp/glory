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
