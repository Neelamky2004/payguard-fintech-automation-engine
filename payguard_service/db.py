import sqlite3
from datetime import datetime

DB_NAME = "payguard_ledger.db"

def init_db():
    with sqlite3.connect(DB_NAME) as conn:
        cursor = conn.cursor()
        cursor.execute("""
            CREATE TABLE IF NOT EXISTS transaction_ledger (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                tx_id TEXT UNIQUE NOT NULL,
                account_id TEXT NOT NULL,
                amount REAL NOT NULL,
                currency TEXT NOT NULL,
                status TEXT NOT NULL,
                processed_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
            );
        """)
        conn.commit()

def record_transaction(tx_id: str, account_id: str, amount: float, currency: str, status: str):
    with sqlite3.connect(DB_NAME) as conn:
        cursor = conn.cursor()
        cursor.execute("""
            INSERT OR REPLACE INTO transaction_ledger (tx_id, account_id, amount, currency, status)
            VALUES (?, ?, ?, ?, ?)
        """, (tx_id, account_id, amount, currency, status))
        conn.commit()

def fetch_audit_summary():
    """SQL Aggregate reporting for analytics"""
    with sqlite3.connect(DB_NAME) as conn:
        cursor = conn.cursor()
        cursor.execute("""
            SELECT 
                status,
                COUNT(*) as total_count,
                ROUND(AVG(amount), 2) as avg_amount,
                ROUND(SUM(amount), 2) as total_volume
            FROM transaction_ledger
            GROUP BY status;
        """)
        return cursor.fetchall()
