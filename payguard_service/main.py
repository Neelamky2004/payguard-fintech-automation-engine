from fastapi import FastAPI, HTTPException
from pydantic import BaseModel, Field
from payguard_service.engine import Transaction, RuleBasedAnomalyDetector

app = FastAPI(title="PayGuard Fintech Automation Engine", version="2.0.0")
detector = RuleBasedAnomalyDetector(max_threshold=50000.0)

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
        return detector.evaluate(tx)
    except Exception as e:
        raise HTTPException(status_code=500, detail=str(e))
