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
