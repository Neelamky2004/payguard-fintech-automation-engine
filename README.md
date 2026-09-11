# PayGuard – Enterprise FinTech Test Automation Framework (UI + API + SQL)

<p>
  <img src="https://img.shields.io/badge/PayGuard_Enterprise_CI-passing-brightgreen" alt="CI"/>
  <img src="https://img.shields.io/badge/Tests-14%2F14_Passed-success" alt="Tests"/>
  <img src="https://img.shields.io/badge/Java-17-blue" alt="Java"/>
  <img src="https://img.shields.io/badge/Selenium-4.18.1-green" alt="Selenium"/>
  <img src="https://img.shields.io/badge/REST_Assured-5.4.0-orange" alt="REST"/>
  <img src="https://img.shields.io/badge/Architecture-3--Tier_FinTech-purple" alt="Arch"/>
</p>

Execution Summary: 14 Executed, 14 Passed, 0 Failed, 0 Skipped (100% Pass Rate)

---

## Core Engineering Capabilities

1. **Flaky Test Resilience:** Integrated RetryAnalyzer and IAnnotationTransformer for automated retries on network fluctuations.
2. **React Dynamic DOM Sync:** Solved async state reconciliation delays in headless execution using explicit WebDriverWait and custom synthetic JavaScript event triggers.
3. **Chromium Profile Hardening:** Handled native Chromium credential warnings via experimental ChromeOptions preferences.
4. **Dual Database Architecture:** Implemented DatabaseManager with dual configurations for JDBC ledger persistence and ACID state verification.
5. **Continuous Integration:** Headless regression workflows executed automatically on every GitHub push via GitHub Actions.

---

## Suite Execution Commands

* Run complete test suite: `mvn clean test`
* Run smoke verification suite: `mvn test -Dgroups=smoke`
* Run sanity validation suite: `mvn test -Dgroups=sanity`