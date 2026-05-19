import pytest
import app.database as db_module
from fastapi.testclient import TestClient


@pytest.fixture
def tmp_db(tmp_path):
    db_file = str(tmp_path / "test.db")
    original = db_module.DB_PATH
    db_module.DB_PATH = db_file
    db_module.init_db()
    yield db_file
    db_module.DB_PATH = original


@pytest.fixture
def client(tmp_db):
    from app.main import app
    return TestClient(app)
