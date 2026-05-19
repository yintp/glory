import pandas as pd
import numpy as np
from ta.trend import MACD


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
    indicator = MACD(df['close'], window_slow=26, window_fast=12, window_sign=9)
    df['macd'] = indicator.macd()
    df['macd_signal'] = indicator.macd_signal()
    df['macd_hist'] = indicator.macd_diff()

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
    df['date'] = df['date'].dt.strftime('%Y-%m-%d').astype(object)
    return df
