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
