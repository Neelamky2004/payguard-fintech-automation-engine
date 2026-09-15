# PayGuard – Enterprise FinTech Automation & Backend Engine (Python + Java + SQL)

<p>
  <img src="https://img.shields.io/badge/Python-3.10+-3776AB?logo=python&logoColor=white" alt="Python"/>
  <img src="https://img.shields.io/badge/FastAPI-REST_API-009688?logo=fastapi&logoColor=white" alt="FastAPI"/>
  <img src="https://img.shields.io/badge/PyTest-Passing-brightgreen?logo=pytest&logoColor=white" alt="PyTest"/>
  <img src="https://img.shields.io/badge/Tests-14%2F14_Passed-success" alt="Tests"/>
  <img src="https://img.shields.io/badge/Java-17-blue" alt="Java"/>
  <img src="https://img.shields.io/badge/Selenium-4.18.1-green" alt="Selenium"/>
  <img src="https://img.shields.io/badge/SQL-JDBC_&_ACID-purple" alt="SQL"/>
</p>

An enterprise-grade FinTech automation framework and backend service designed to process high-throughput multi-currency transactions, execute statistical anomaly detection, and perform end-to-end API, UI, and database verification.

---

## Core Engineering Capabilities

1. **Python REST Backend & Service Layer:** Built high-performance asynchronous REST endpoints using **FastAPI** and **Pydantic** for real-time transaction ingestion and status monitoring.
2. **Rule-Based Anomaly Detection:** Implemented OOP-driven evaluation engines in Python (`payguard_service`) to detect statistical outliers, threshold breaches, and invalid amounts prior to ledger execution.
3. **Automated Unit Testing & SDLC:** Complete test harness using **PyTest** for validation logic alongside continuous regression workflows.
4. **Dual Database & SQL Verification:** Implemented `DatabaseManager` with dual configurations for JDBC ledger persistence, transactional state assertions, and ACID compliance.
5. **Dynamic DOM & API Synchronization:** Solved asynchronous state reconciliation delays in headless execution using explicit `WebDriverWait` and native synthetic JavaScript event triggers.
6. **Flaky Test Resilience:** Integrated `RetryAnalyzer` and `IAnnotationTransformer` for automated retries against network fluctuation.

---

## Suite Execution Commands

### 1. Python Automation Engine & REST API
```bash
# Install dependencies
pip install -r requirements.txt

# Run automated unit tests (PyTest)
pytest tests/

# Start FastAPI backend service
uvicorn payguard_service.main:app --reload