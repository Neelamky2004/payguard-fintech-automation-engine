import pytest
import sqlite3
from payguard_service.db import init_db, record_transaction, DB_NAME

def test_sql_ledger_persistence():
    init_db()
    record_transaction("TEST_TX_99", "ACC_TEST", 1200.0, "INR", "APPROVED")
    
    with sqlite3.connect(DB_NAME) as conn:
        cursor = conn.cursor()
        cursor.execute("SELECT status FROM transaction_ledger WHERE tx_id = 'TEST_TX_99';")
        row = cursor.fetchone()
        assert row is not None
        assert row[0] == "APPROVED"
