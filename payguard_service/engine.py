from datetime import datetime

class Transaction:
    def __init__(self, tx_id: str, account_id: str, amount: float, currency: str = "INR"):
        self.tx_id = tx_id
        self.account_id = account_id
        self.amount = amount
        self.currency = currency
        self.timestamp = datetime.utcnow().isoformat()

class RuleBasedAnomalyDetector:
    def __init__(self, max_threshold: float = 50000.0):
        self.max_threshold = max_threshold

    def evaluate(self, transaction: Transaction) -> dict:
        flags = []
        is_suspicious = False

        if transaction.amount <= 0:
            flags.append("INVALID_AMOUNT")
            is_suspicious = True
        elif transaction.amount > self.max_threshold:
            flags.append("HIGH_VALUE_OUTLIER")
            is_suspicious = True

        status = "FLAGGED" if is_suspicious else "APPROVED"
        return {
            "tx_id": transaction.tx_id,
            "account_id": transaction.account_id,
            "amount": transaction.amount,
            "status": status,
            "flags": flags,
            "processed_at": transaction.timestamp
        }
