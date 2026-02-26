# -*- coding: utf-8 -*-
"""
多因子ETF轮动策略回测
作者：殷自豪
策略规则：
  买入（全部满足）：
    1. 周MA20 > 月MA12
    2. 成交量 > 20日均量
  卖出（全部满足）：
    1. 周MA20 < 月MA12
    2. 成交量 > 20日均量
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
END_DATE = "20260225"
INITIAL_CAPITAL = 1_000_000  # 100万元
COMMISSION = 0.0003  # 0.03%


# ========== 数据获取与预处理 ==========
def get_etf_data(symbol, debug):
    print(f"开始【{symbol}】数据获取与预处理...")
    try:
        df = ak.fund_etf_hist_sina(symbol=f"sz{symbol}" if symbol[0] in ['0','1'] else f"sh{symbol}")
    except:
        try:
            df = ak.fund_etf_hist_sina(symbol=f"sh{symbol}")
        except:
            print(f"❌ 无法获取 【{symbol}】 数据")
            return None
    df['date'] = pd.to_datetime(df['date'])
    df = df[(df['date'] >= START_DATE) & (df['date'] <= END_DATE)].copy()
    df.sort_values('date', inplace=True)
    df.reset_index(drop=True, inplace=True)

    if debug:
        print(f"√ 【{symbol}】原始数据获取完成\n{df.tail(20)}")

    return df[['date', 'close', 'volume']]


# ========== 计算指标 ==========
def calculate_indicators(df, symbol, debug):
    print(f"开始【{symbol}】计算指标...")
    # 周线 & 月线数据（通过重采样）
    weekly = df.set_index('date').resample('W-FRI').last()
    monthly = df.set_index('date').resample('ME').last()

    if debug:
        print(f"√ 【{symbol}】日线基本数据准备完成\n{df.tail(20)}")
        print(f"√ 【{symbol}】周线基本数据准备完成\n{weekly.tail(20)}")
        print(f"√ 【{symbol}】月线基本数据准备完成\n{monthly.tail(12)}")

    # 日线指标
    df['ma20'] = df['close'].rolling(20).mean()
    df['vol_ma20'] = df['volume'].rolling(20).mean()

    # MACD (12,26,9)
    df['macd'] = macd(df['close'])
    df['macd_signal'] = macd_signal(df['close'])
    df['macd_hist'] = df['macd'] - df['macd_signal']

    if debug:
        print(f"√ 【{symbol}】计算日线指标完成\n{df.tail(20)}")

    # 周线 & 月线指标
    weekly['w_ma20'] = weekly['close'].rolling(20).mean()
    monthly['m_ma12'] = monthly['close'].rolling(12).mean()

    if debug:
        print(f"√ 【{symbol}】计算周线指标完成\n{weekly.tail(20)}")
        print(f"√ 【{symbol}】计算月线指标完成\n{monthly.tail(12)}")

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
        print(f"√ 【{symbol}】日线映射整合周、月线，填补缺失指标完成\n{df.tail(20)}")

    return df


# ========== 生成基础信号 ==========
def generate_basic_signals(df, symbol, debug):
    print(f"开始【{symbol}】生成基础信号...")
    for i in range(2, len(df)):
        # 基础信号
        basic_signal1 = df['w_ma20'].iloc[i] > df['m_ma12'].iloc[i]
        basic_signal2 = df['volume'].iloc[i] > df['vol_ma20'].iloc[i]
        basic_signal3 = (df['w_ma20'].iloc[i] > df['m_ma12'].iloc[i]) and (df['w_ma20'].iloc[i-1] <= df['m_ma12'].iloc[i-1])
        basic_signal4 = df['w_ma20'].iloc[i] < df['m_ma12'].iloc[i]
        basic_signal5 = (df['w_ma20'].iloc[i] < df['m_ma12'].iloc[i]) and (df['w_ma20'].iloc[i-1] >= df['m_ma12'].iloc[i-1])

        # 生成信号
        df.loc[i, 'basic_signal1'] = int(basic_signal1)
        df.loc[i, 'basic_signal2'] = int(basic_signal2)
        df.loc[i, 'basic_signal3'] = int(basic_signal3)
        df.loc[i, 'basic_signal4'] = int(basic_signal4)
        df.loc[i, 'basic_signal5'] = int(basic_signal5)

        if debug and i >= len(df) - 1:
            print(f"【{symbol}】最后一次数据：")
            print(f"【{symbol}】basic_signal1: {basic_signal1}={df['w_ma20'].iloc[i]}>{df['m_ma12'].iloc[i]}")
            print(f"【{symbol}】asic_signal2: {basic_signal2}={df['volume'].iloc[i]}>{df['vol_ma20'].iloc[i]}")
            print(f"【{symbol}】basic_signal3: {basic_signal3}=({df['w_ma20'].iloc[i]} > {df['m_ma12'].iloc[i]} and {df['w_ma20'].iloc[i-1]} <= {df['m_ma12'].iloc[i-1]})")
            print(f"【{symbol}】basic_signal4: {basic_signal4}={df['w_ma20'].iloc[i]}<{df['m_ma12'].iloc[i]}")
            print(f"【{symbol}】basic_signal5: {basic_signal5}=({df['w_ma20'].iloc[i]} < {df['m_ma12'].iloc[i]} and {df['w_ma20'].iloc[i-1]} >= {df['m_ma12'].iloc[i-1]})")

    if debug:
        print(f"√ 【{symbol}】日线整合基础信号完成\n{df.tail(20)}")

    return df


# ========== 生成买卖点信号 ==========
def generate_buy_sell_signals(df, symbol, debug):
    print(f"开始【{symbol}】生成买卖点信号...")
    for i in range(2, len(df)):
        df.loc[i, 'buy_signal'] = int(df['basic_signal3'].iloc[i] == 1 and df['basic_signal2'].iloc[i] == 1)
        df.loc[i, 'sell_signal'] = int(df['basic_signal5'].iloc[i] == 1 and df['basic_signal2'].iloc[i] == 1)

    if debug:
        print(f"√ 【{symbol}】日线整合买卖点信号完成\n{df.tail(20)}")

        # 打印df中buy_signal或sell_signal为1的数据
        filtered_df = df[(df['buy_signal'] == 1) | (df['sell_signal'] == 1)]
        print(f"【{symbol}】buy_signal或sell_signal为1的数据：\n{filtered_df.tail(20)}")

    return df


# ========== 主回测 ==========
def backtest(debug):
    print(f"开始主回测...")
    all_data = {}

    # 获取并处理每只ETF数据
    for symbol in ETF_LIST:
        df = get_etf_data(symbol, debug)
        if df is None or len(df) < 100:
            continue
        df = calculate_indicators(df, symbol, debug)
        df = generate_basic_signals(df, symbol, debug)
        df = generate_buy_sell_signals(df, symbol, debug)
        all_data[symbol] = df

    # 组合回测
    dates = pd.date_range(start=START_DATE, end=END_DATE, freq='D')
    portfolio = pd.DataFrame(index=dates, columns=['equity', 'holdings', 'cash'])
    portfolio['equity'] = float(INITIAL_CAPITAL)
    portfolio['cash'] = float(INITIAL_CAPITAL)
    portfolio['holdings'] = 0.0

    # 初始化前一日现金
    prev_cash = float(INITIAL_CAPITAL)
    # {symbol: shares}
    current_holdings = {}
    trade_log = []

    for date in dates:
        if date not in portfolio.index:
            continue

        # 继承前一日的现金余额
        portfolio.loc[date, 'cash'] = prev_cash

        # 检查当日是否有ETF发出信号
        buy_list = []
        sell_list = []

        for symbol in all_data:
            df = all_data[symbol]
            if date in df['date'].values:
                row = df[df['date'] == date].iloc[0]
                if row['buy_signal'] == 1:
                    buy_list.append(symbol)
                if row['sell_signal'] == 1 and symbol in current_holdings:
                    sell_list.append(symbol)

        # 先处理卖出
        if sell_list:
            available_cash = portfolio.loc[date, 'cash']
            if debug:
                print(f"【买卖记录】日期: {date}, 可用现金: {available_cash}, 卖出列表: {sell_list}")
            for symbol in sell_list:
                df = all_data[symbol]
                price = df[df['date'] == date]['close'].values[0]
                shares = current_holdings[symbol]
                amount = shares * price * (1 - COMMISSION)
                portfolio.loc[date, 'cash'] += amount
                if debug:
                    print(f"【买卖记录】日期: {date} 卖出 {symbol}: 价格={price}, 份额={shares}, 获得金额={amount}")
                del current_holdings[symbol]
                trade_log.append({
                    'date': date, 'symbol': symbol, 'action': 'SELL',
                    'price': price, 'shares': shares, 'amount': amount
                })

        # 再处理买入（用当前现金等分）
        if buy_list:
            available_cash = portfolio.loc[date, 'cash']
            if debug:
                print(f"【买卖记录】日期: {date}, 可用现金: {available_cash}, 买入列表: {buy_list}")
            per_cash = available_cash / len(buy_list)
            for symbol in buy_list:
                df = all_data[symbol]
                price = df[df['date'] == date]['close'].values[0]
                # 以100份为单位
                shares = int((per_cash * (1 - COMMISSION)) // (price * 100)) * 100
                if shares > 0:
                    cost = shares * price * (1 + COMMISSION)
                    if debug:
                        print(f"【买卖记录】日期: {date} 买入 {symbol}: 价格={price}, 份额={shares}, 成本={cost}")
                    portfolio.loc[date, 'cash'] -= cost
                    current_holdings[symbol] = shares
                    trade_log.append({
                        'date': date, 'symbol': symbol, 'action': 'BUY',
                        'price': price, 'shares': shares, 'amount': cost
                    })

        # 计算当日持仓市值
        holding_value = 0
        for symbol, shares in current_holdings.items():
            df = all_data[symbol]
            if date in df['date'].values:
                price = df[df['date'] == date]['close'].values[0]
                holding_value += shares * price
                if debug and date >= pd.Timestamp.now() - pd.Timedelta(days=20):
                    print(f"【买卖记录】持仓详情 日期: {date}, {symbol} {shares}份 @ {price}元 = {shares * price}元")
        portfolio.loc[date, 'holdings'] = holding_value
        portfolio.loc[date, 'equity'] = portfolio.loc[date, 'cash'] + holding_value

        if debug and date >= pd.Timestamp.now() - pd.Timedelta(days=20):
            print(f"【买卖记录】当日总结: date={date}, cash={portfolio.loc[date, 'cash']}, holdings={holding_value}, equity={portfolio.loc[date, 'equity']}")

        # 更新前一日现金余额
        prev_cash = portfolio.loc[date, 'cash']

    if debug:
        print(f"√ portfolio主回测数据完成\n{portfolio.tail(20)}")
        print(f"√ trade_log主回测完整交易记录")
        for record in trade_log:
            print(f"  {record['date'].strftime('%Y-%m-%d')}: {record['action']} {record['symbol']} @ {record['shares']}份 {record['price']: .3f}元 共{record['amount']: .2f}元")
        for symbol, df in all_data.items():
            print(f"√ {ETF_NAMES.get(symbol, symbol)}all_data主回测数据完成\n{df.tail(20)}")

    return portfolio, trade_log, all_data


# ========== 主程序 ==========
if __name__ == "__main__":
    # 设置 pandas 显示选项
    pd.set_option('display.max_columns', None)
    pd.set_option('display.width', None)
    pd.set_option('display.max_colwidth', None)
    pd.set_option('display.float_format', '{:.2f}'.format)

    print("🚀 开始...")
    basket, trade_record, etf_data = backtest(True)
