# PayGuard QA Defect Management & Tracking Log

This document records functional and reconciliation defects identified, tracked, and verified during automated regression cycles on the PayGuard Core Banking & Settlement platform.

---

### DEFECT-101: Negative Transaction Volume Acceptance in Disbursement Rails
* **Severity**: Critical (P1) | **Priority**: High (S1)
* **Component**: UI & Payment API Routing Engine
* **Environment**: Fintech-Staging-01
* **Reported By**: Neelam Kumari (SDET Lead)
* **Status**: CLOSED (Verified via Automated Regression)

#### Description:
Disbursement payload with negative numeric float values was accepted by settlement rails without rejecting on input boundary.
* **Steps to Reproduce**:
  1. Authenticate via PayGuard portal.
  2. Initiate bulk settlement batch with amount `-12500.00`.
  3. Submit clearance payload via FED_WIRE rail.
* **Expected Result**: Validation exception with HTTP 422 Unprocessable Entity; UI renders `Invalid Transfer Amount`.
* **Actual Result**: Payload reached gateway staging ledger.
* **Resolution**: Added client-side Regex validation (`amount >= 100.00`) and API contract schema rule. Verified in `PayGuardUITest.testMultiRailSettlementMatrix`.

---

### DEFECT-102: Ledger Status Variance on Failed Settlement Postings
* **Severity**: High (P2) | **Priority**: High (S2)
* **Component**: JDBC Database Persistence Layer
* **Status**: CLOSED (Verified)

#### Description:
When payment gateway returned non-200 responses, the in-memory ledger was persisting dangling transactions with status `PENDING` instead of executing rollback to `FAILED`.
* **Resolution**: Implemented JDBC transaction rollback hooks in `DBUtil.java`. Verified via atomic reconciliation suite.
