from fastapi import FastAPI
from fastapi.staticfiles import StaticFiles
from fastapi.responses import FileResponse
import os
from app.database import init_db
from app.routes import etf, analysis, update

app = FastAPI(title="ETF分析平台")
init_db()
app.include_router(etf.router, prefix="/api")
app.include_router(analysis.router, prefix="/api")
app.include_router(update.router, prefix="/api")

FRONTEND = os.path.join(os.path.dirname(__file__), '..', 'frontend')
if os.path.isdir(FRONTEND):
    app.mount("/static", StaticFiles(directory=FRONTEND), name="static")

    @app.get("/")
    def index():
        return FileResponse(os.path.join(FRONTEND, 'index.html'))
