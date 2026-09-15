import pytest
from payguard_service.engine import Transaction, RuleBasedAnomalyDetector

def test_approved_transaction():
    detector = RuleBasedAnomalyDetector(max_threshold=50000.0)
    tx = Transaction(tx_id="T1", account_id="A1", amount=1500.0)
    res = detector.evaluate(tx)
    assert res["status"] == "APPROVED"
    assert len(res["flags"]) == 0

def test_flagged_high_value_transaction():
    detector = RuleBasedAnomalyDetector(max_threshold=50000.0)
    tx = Transaction(tx_id="T2", account_id="A1", amount=95000.0)
    res = detector.evaluate(tx)
    assert res["status"] == "FLAGGED"
    assert "HIGH_VALUE_OUTLIER" in res["flags"]

def test_invalid_negative_amount():
    detector = RuleBasedAnomalyDetector()
    tx = Transaction(tx_id="T3", account_id="A2", amount=-10.0)
    res = detector.evaluate(tx)
    assert res["status"] == "FLAGGED"
    assert "INVALID_AMOUNT" in res["flags"]
