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
