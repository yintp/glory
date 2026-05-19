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
