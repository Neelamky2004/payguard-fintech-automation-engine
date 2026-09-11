# Test Cases Documentation Matrix

| Test Case ID | Layer | Test Scenario | Test Method | Type | Expected Result | Status |
|---|---|---|---|---|---|---|
| TC-AUTH-01 | UI / Security | Invalid login rejection | 	estInvalidLogin | Negative, Smoke | Access denied error displayed | Pass |
| TC-DASH-01 | UI | Enterprise dashboard load | 	estDashboardAvailability | Smoke, UI | Dashboard widgets render fully | Pass |
| TC-PAY-01 | API / E2E | Cross-border payment clearance | unScenario | E2E, Regression | Transaction processed successfully | Pass |
| TC-LEDG-01 | Database | Ledger balance update | 	estLedgerSettlement | Database, Sanity | Balances match expected delta | Pass |
