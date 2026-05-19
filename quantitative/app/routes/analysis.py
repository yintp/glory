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
