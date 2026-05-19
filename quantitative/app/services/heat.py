import sqlite3


def compute_heat_scores(conn: sqlite3.Connection):
    """
    Recompute heat_score for every active ETF.
    heat_score = latest_volume / vol_ma20.  0.0 when vol_ma20 is unavailable.
    """
    c = conn.cursor()
    c.execute("SELECT symbol FROM etf_list WHERE active = 1")
    symbols = [row[0] for row in c.fetchall()]

    for symbol in symbols:
        c.execute("""
            SELECT d.volume, i.vol_ma20
            FROM etf_daily d
            JOIN etf_indicators i ON d.symbol = i.symbol AND d.date = i.date
            WHERE d.symbol = ?
            ORDER BY d.date DESC
            LIMIT 1
        """, (symbol,))
        row = c.fetchone()
        heat = (row[0] / row[1]) if (row and row[1] and row[1] > 0) else 0.0

        c.execute("""
            UPDATE etf_indicators SET heat_score = ?
            WHERE symbol = ? AND date = (
                SELECT MAX(date) FROM etf_indicators WHERE symbol = ?
            )
        """, (heat, symbol, symbol))

    conn.commit()
