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
            c.execute("UPDATE etf_list SET active=1, name=? WHERE symbol=?",
                      (body.name, body.symbol))
            conn.commit()
        else:
            c.execute(
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
        c.execute("UPDATE etf_list SET name=? WHERE symbol=?", (body.name, symbol))
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
        c.execute("UPDATE etf_list SET active=0 WHERE symbol=?", (symbol,))
        conn.commit()
        return {"status": "ok"}
    finally:
        conn.close()
