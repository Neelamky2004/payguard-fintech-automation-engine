# PayGuard – Enterprise FinTech Automation & Backend Engine (Python + Java + SQL)

<p>
  <img src="https://img.shields.io/badge/Python-3.10+-3776AB?logo=python&logoColor=white" alt="Python"/>
  <img src="https://img.shields.io/badge/FastAPI-REST_API-009688?logo=fastapi&logoColor=white" alt="FastAPI"/>
  <img src="https://img.shields.io/badge/PyTest-Passing-brightgreen?logo=pytest&logoColor=white" alt="PyTest"/>
  <img src="https://img.shields.io/badge/SQLite-Ledger-003B57?logo=sqlite&logoColor=white" alt="SQLite"/>
  <img src="https://img.shields.io/badge/Tests-14%2F14_Passed-success" alt="Tests"/>
  <img src="https://img.shields.io/badge/Java-17-blue" alt="Java"/>
  <img src="https://img.shields.io/badge/Selenium-4.18.1-green" alt="Selenium"/>
  <img src="https://img.shields.io/badge/Architecture-3--Tier_FinTech-purple" alt="Arch"/>
</p>

An enterprise-grade financial transaction processing, rule-based anomaly validation, and test automation framework. The system combines a lightweight Python FastAPI backend service with a multi-tier Java test orchestration suite to validate high-throughput multi-currency settlements across banking rails, enforce schema validation, detect computational outliers, and guarantee ACID ledger persistence.

---

## Architecture Overview

```text
[ Client / Webhook ] 
         │ (HTTP POST JSON)
         ▼
┌────────────────────────────────────────────────────────┐
│  Python Validation Service (FastAPI & Pydantic)       │
│  - Endpoint: /api/v1/validate-transaction              │
│  - Schema Parsing & Sanitization                       │
└───────────────────────┬────────────────────────────────┘
                        │
                        ▼
┌────────────────────────────────────────────────────────┐
│  Rule-Based Computational Anomaly Detector             │
│  - Zero / Negative Value Rejection                     │
│  - Threshold Outlier Flagging (> 50,000 INR)          │
│  - ISO-8601 Audit Timestamping                         │
└───────────────────────┬────────────────────────────────┘
                        │
                        ▼
┌────────────────────────────────────────────────────────┐
│  SQLite Transactional Ledger (db.py)                   │
│  - ACID Table: transaction_ledger                      │
│  - Aggregated Audit Reporting: /api/v1/analytics       │
└────────────────────────────────────────────────────────┘