from fastapi import FastAPI, HTTPException
from pydantic import BaseModel, Field
from payguard_service.engine import Transaction, RuleBasedAnomalyDetector
from payguard_service.db import init_db, record_transaction, fetch_audit_summary

app = FastAPI(title="PayGuard Fintech Automation Engine", version="2.1.0")
detector = RuleBasedAnomalyDetector(max_threshold=50000.0)

# Initialize SQL Schema on start
init_db()

class TransactionPayload(BaseModel):
    tx_id: str = Field(..., example="TXN1001")
    account_id: str = Field(..., example="ACC8892")
    amount: float = Field(..., gt=0, example=12500.0)
    currency: str = Field(default="INR", example="INR")

@app.get("/health")
def health_check():
    return {"status": "healthy", "service": "payguard-automation-engine"}

@app.post("/api/v1/validate-transaction")
def validate_transaction(payload: TransactionPayload):
    try:
        tx = Transaction(
            tx_id=payload.tx_id,
            account_id=payload.account_id,
            amount=payload.amount,
            currency=payload.currency
        )
        result = detector.evaluate(tx)
        
        # Persist to SQL ledger
        record_transaction(
            tx_id=result["tx_id"],
            account_id=result["account_id"],
            amount=result["amount"],
            currency=payload.currency,
            status=result["status"]
        )
        return result
    except Exception as e:
        raise HTTPException(status_code=500, detail=str(e))

@app.get("/api/v1/analytics/summary")
def get_ledger_analytics():
    """Returns aggregated metrics directly from SQL database"""
    try:
        data = fetch_audit_summary()
        summary = [{"status": row[0], "count": row[1], "avg_amount": row[2], "total_volume": row[3]} for row in data]
        return {"ledger_summary": summary}
    except Exception as e:
        raise HTTPException(status_code=500, detail=str(e))
