# -*- coding: utf-8 -*-
"""
多因子ETF轮动策略回测
作者：殷自豪
策略规则：
  买入（全部满足）：
    1. 周MA20 > 月MA12
    2. 日MA20 上穿 周MA20
    3. MACD金叉 + 红柱放大 + DIF>0
    4. 成交量 > 20日均量
  卖出（全部满足）：
    1. 周MA20 < 月MA12
    2. 日MA20 下穿 周MA20
    3. MACD死叉 + 绿柱放大 + DIF<0
    4. 成交量 > 20日均量
"""

import akshare as ak
import pandas as pd
import numpy as np
import matplotlib.pyplot as plt
import matplotlib.dates as mdates
from ta.trend import macd, macd_signal, macd_diff
import warnings
warnings.filterwarnings('ignore')

# ========== 参数配置 ==========
ETF_LIST = [
    "515790"
]
ETF_NAMES = {
    "515790": "光伏ETF"
}
START_DATE = "20180101"
END_DATE = "20260204"
INITIAL_CAPITAL = 1_000_000  # 100万元
COMMISSION = 0.0003  # 0.03%

# ========== 数据获取与预处理 ==========
def get_etf_data(symbol, debug):
    try:
        df = ak.fund_etf_hist_sina(symbol=f"sz{symbol}" if symbol[0] in ['0','1'] else f"sh{symbol}")
    except:
        try:
            df = ak.fund_etf_hist_sina(symbol=f"sh{symbol}")
        except:
            print(f"❌ 无法获取 {symbol} 数据")
            return None
    df['date'] = pd.to_datetime(df['date'])
    df = df[(df['date'] >= START_DATE) & (df['date'] <= END_DATE)].copy()
    df.sort_values('date', inplace=True)
    df.reset_index(drop=True, inplace=True)

    if debug:
        print(f"√ 【{symbol}】原始数据获取完成\n{df.tail(20)}")

    return df[['date', 'close', 'volume']]

# ========== 计算指标 ==========
def calculate_indicators(df, debug):
    # 周线 & 月线数据（通过重采样）
    weekly = df.set_index('date').resample('W-FRI').last()
    monthly = df.set_index('date').resample('ME').last()

    if debug:
        print(f"√ 日线基本数据准备完成\n{df.tail(20)}")
        print(f"√ 周线线基本数据准备完成\n{weekly.tail(20)}")
        print(f"√ 月线线基本数据准备完成\n{monthly.tail(12)}")

    # 日线指标
    df['ma20'] = df['close'].rolling(20).mean()
    df['vol_ma20'] = df['volume'].rolling(20).mean()

    # MACD (12,26,9)
    df['macd'] = macd(df['close'])
    df['macd_signal'] = macd_signal(df['close'])
    df['macd_hist'] = df['macd'] - df['macd_signal']

    if debug:
        print(f"√ 计算日线指标完成\n{df.tail(20)}")

    # 周线 & 月线指标
    weekly['w_ma20'] = weekly['close'].rolling(20).mean()
    monthly['m_ma12'] = monthly['close'].rolling(12).mean()

    if debug:
        print(f"√ 计算周线指标完成\n{weekly.tail(20)}")
        print(f"√ 计算月线指标完成\n{monthly.tail(12)}")

    # 映射回日线
    df.set_index('date', inplace=True)
    df['w_ma20'] = np.nan
    df['m_ma12'] = np.nan

    for idx in weekly.index:
        if idx in df.index:
            df.at[idx, 'w_ma20'] = weekly.at[idx, 'w_ma20']
    for idx in monthly.index:
        if idx in df.index:
            df.at[idx, 'm_ma12'] = monthly.at[idx, 'm_ma12']

    # 填补缺失值
    df.ffill(inplace=True)
    df.reset_index(inplace=True)

    if debug:
        print(f"√ 日线映射整合周、月线，填补缺失指标完成\n{df.tail(20)}")

    return df

# ========== 生成信号 ==========
def generate_signals(df, debug):
    df['buy_sell'] = np.nan
    df['ma_cross'] = np.nan
    df['macd_cross'] = np.nan
    df['buy_sell_point_single'] = np.nan

    for i in range(2, len(df)):
        # 买入条件
        buy_cond1 = df['w_ma20'].iloc[i] > df['m_ma12'].iloc[i]
        buy_cond2 = (df['ma20'].iloc[i] > df['w_ma20'].iloc[i])
        buy_cond3 = (df['macd'].iloc[i] > df['macd_signal'].iloc[i]) and (df['macd_hist'].iloc[i] > df['macd_hist'].iloc[i-1]) and (df['macd'].iloc[i] > 0)
        buy_cond4 = df['volume'].iloc[i] > df['vol_ma20'].iloc[i]

        if debug and i >= len(df) - 1:
            print(f"buy_cond1: {buy_cond1}={df['w_ma20'].iloc[i]}>{df['m_ma12'].iloc[i]}")
            print(f"buy_cond2: {buy_cond2}={df['ma20'].iloc[i]}>{df['w_ma20'].iloc[i]}")
            print(f"buy_cond3: {buy_cond3}=({df['macd'].iloc[i]} > {df['macd_signal'].iloc[i]}) and ({df['macd_hist'].iloc[i]} > {df['macd_hist'].iloc[i-1]}) and ({df['macd'].iloc[i]} > 0)")
            print(f"buy_cond4: {buy_cond4}={df['volume'].iloc[i]}>{df['vol_ma20'].iloc[i]}")

        # 卖出条件
        sell_cond1 = df['w_ma20'].iloc[i] < df['m_ma12'].iloc[i]
        sell_cond2 = (df['ma20'].iloc[i] < df['w_ma20'].iloc[i])
        sell_cond3 = (df['macd'].iloc[i] < df['macd_signal'].iloc[i]) and (df['macd_hist'].iloc[i] < df['macd_hist'].iloc[i-1]) and (df['macd'].iloc[i] < 0)
        sell_cond4 = df['volume'].iloc[i] > df['vol_ma20'].iloc[i]

        if debug and i >= len(df) - 1:
            print(f"sell_cond1: {sell_cond1}={df['w_ma20'].iloc[i]}<{df['m_ma12'].iloc[i]}")
            print(f"sell_cond2: {sell_cond2}={df['ma20'].iloc[i]}<{df['w_ma20'].iloc[i]}")
            print(f"sell_cond3: {sell_cond3}=({df['macd'].iloc[i]} < {df['macd_signal'].iloc[i]}) and ({df['macd_hist'].iloc[i]} < {df['macd_hist'].iloc[i-1]}) and ({df['macd'].iloc[i]} < 0)")
            print(f"sell_cond4: {sell_cond4}={df['volume'].iloc[i]}>{df['vol_ma20'].iloc[i]}")

        # 生成信号
        buy = int(buy_cond1 and buy_cond4)
        sell = int(sell_cond1 and sell_cond4)
        df.loc[i, 'buy_sell'] = buy + sell

        if debug and i >= len(df) - 1:
            print(f"buy_sell: {df.loc[i, 'buy_sell']} buy_cond1: {buy_cond1} buy_cond2: {buy_cond2} buy_cond3: {buy_cond3} buy_cond4: {buy_cond4} sell_cond1: {sell_cond1} sell_cond2: {sell_cond2} sell_cond3: {sell_cond3} sell_cond4: {sell_cond4}")

        # 计算 ma20 和 w_ma20 的交叉信号
        diff_current = df['ma20'].iloc[i] - df['w_ma20'].iloc[i]
        diff_previous = df['ma20'].iloc[i-1] - df['w_ma20'].iloc[i-1]
        if diff_current > 0 and diff_previous <= 0:
            # 向上交叉
            df.loc[i, 'ma_cross'] = 1
        elif diff_current < 0 and diff_previous >= 0:
            # 向下交叉
            df.loc[i, 'ma_cross'] = -1

        if debug and i >= len(df) - 1:
            print(f"diff_current: {diff_current}={df['ma20'].iloc[i]}-{df['w_ma20'].iloc[i]} ")
            print(f"diff_previous: {diff_previous}={df['ma20'].iloc[i-1]}-{df['w_ma20'].iloc[i-1]} ")

        # 计算 macd 的交叉信号
        macd_diff_current = df['macd'].iloc[i] - df['macd_signal'].iloc[i]
        macd_diff_previous = df['macd'].iloc[i-1] - df['macd_signal'].iloc[i-1]
        if macd_diff_current > 0 and macd_diff_previous <= 0:
            # 向上交叉
            df.loc[i, 'macd_cross'] = 1
        elif macd_diff_current < 0 and macd_diff_previous >= 0:
            # 向下交叉
            df.loc[i, 'macd_cross'] = -1

        if debug and i >= len(df) - 1:
            print(f"macd_diff_current: {macd_diff_current}={df['macd'].iloc[i]}-{df['macd_signal'].iloc[i]} ")
            print(f"macd_diff_previous: {macd_diff_previous}={df['macd'].iloc[i-1]}-{df['macd_signal'].iloc[i-1]} ")

    # 填补缺失值
    df.ffill(inplace=True)
    df.reset_index(inplace=True)

    for i in range(2, len(df)):
        # 计算买卖点信号
        buy_point_single = int(df.loc[i, 'buy_sell'] == 1)
        sell_point_single = int(df.loc[i, 'buy_sell'] == -1)
        df.loc[i, 'buy_sell_point_single'] = buy_point_single + sell_point_single

    if debug:
        print(f"√ 日线整合买点卖点信号完成\n{df.tail(20)}")

    return df

# ========== 主程序 ==========
if __name__ == "__main__":
    # 设置 pandas 显示选项
    pd.set_option('display.max_columns', None)
    pd.set_option('display.width', None)
    pd.set_option('display.max_colwidth', None)

    print("🚀 开始回测...")
    df = get_etf_data(515790, False)
    df = calculate_indicators(df, False)
    df = generate_signals(df, False)

    filtered_df = df[df['buy_sell_point_single'].isin([1, -1])]
    print(f"√ 日线整合买点卖点信号完成\n{filtered_df[['date', 'buy_sell_point_single']]}")
