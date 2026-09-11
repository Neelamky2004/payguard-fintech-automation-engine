# PayGuard – Enterprise FinTech Test Automation Framework (UI + API + SQL)

<p>
  <img src="https://img.shields.io/badge/PayGuard_Enterprise_CI-passing-brightgreen" alt="CI"/>
  <img src="https://img.shields.io/badge/Tests-14%2F14_Passed-success" alt="Tests"/>
  <img src="https://img.shields.io/badge/Java-17-blue" alt="Java"/>
  <img src="https://img.shields.io/badge/Selenium-4.18.1-green" alt="Selenium"/>
  <img src="https://img.shields.io/badge/REST_Assured-5.4.0-orange" alt="REST"/>
  <img src="https://img.shields.io/badge/Architecture-3--Tier_FinTech-purple" alt="Arch"/>
</p>

A production-ready hybrid automation framework architected in Java 17 delivering synchronized end-to-end verification across Presentation (UI), FinTech Service (REST API), and Persistence (JDBC Ledger) tiers.

---

## Live Test Execution Matrix (14/14 Scenarios Passing)

| Scenario ID | Layer | Test Method | Target / Verification | Result |
| :--- | :--- | :--- | :--- | :--- |
| **TC-01** | UI | PayGuardUITest.testAuthenticationSecurityBoundary | Blank/Null credentials negative validation | PASS |
| **TC-02** | UI | PayGuardUITest.testFinancialDbashboardIntegrity | Multi-metric balance, ledger rows & merchant descriptors | PASS |
| **TC-03** | UI | PayGuardUITest.testLedgerSortingAndVolumeCalculations | Table sorting event & absolute turnover parsing | PASS |
| **TC-04** | UI | PayGuardUITest.testMultiRailSettlementMatrix | DataProvider multi-currency bulk batch routing | PASS |
| **TC-05** | API | PayGuardAPITest.testPaymentGatewayStatus | Gateway HTTP 200 response & SLA latency check | PASS |
| **TC-06** | API | PayGuardAPITest.testSecurePaymentTokenCreation | Secure settlement token payload contract validation | PASS |
| **TC-07** | API | PayGuardAPITest.testUnauthorizedPaymentToken | Negative HTTP 401/403 authorization guard | PASS |
| **TC-08** | API | PayGuardAPITest.testInvalidPaymentPayload | Negative HTTP 400/422 bad request handling | PASS |
| **TC-09** | API | PayGuardAPITest.testGatewayResourceNotFound | Negative HTTP 404 endpoint boundary check | PASS |
| **TC-10** | SQL | PayGuardDBTest.testTransactionPersistence | JDBC PreparedStatement ledger insertion & retrieval | PASS |
| **TC-11** | E2E | PayGuardEndToEndReconciliationTest.verifyEndToEndPaymentSettlementReconciliation | 3-Tier Atomic Reconciliation (API -> JDBC -> ACID State) | PASS |
| **TC-12** | BDD | PaymentSteps.runScenario (FED_WIRE) | Multi-currency bulk clearance via FED_WIRE rail | PASS |
| **TC-13** | BDD | PaymentSteps.runScenario (SEPA) | Multi-currency bulk clearance via SEPA instant rail | PASS |
| **TC-14** | BDD | PaymentSteps.runScenario (RBI_RTGS) | Multi-currency bulk clearance via RBI RTGS rail | PASS |

---

## Quick Execution Instructions

`bash
# Run full enterprise regression suite
mvn clean test

# Run smoke verification suite
mvn test -Dgroups=smoke

# Run sanity validation suite
mvn test -Dgroups=sanity
`


