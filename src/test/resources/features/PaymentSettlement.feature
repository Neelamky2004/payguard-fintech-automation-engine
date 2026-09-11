@Regression @FintechCore
Feature: Automated Payment Gateway & Cross-Tier Ledger Settlement

  Scenario Outline: End-to-end multi-currency payment clearance across banking rails
    Given User is authorized on the PayGuard FinTech platform
    When Treasury executes a bulk batch "<BatchId>" in "<Currency>" with amount <Amount> on rail "<Rail>"
    Then The gateway must confirm transaction settlement with HTTP 201
    And The persistent database ledger must reflect "<Currency>" state as "SETTLED"

    Examples:
      | BatchId       | Currency | Amount   | Rail                  |
      | BATCH_DEL_001 | USD      | 45000.00 | FED_WIRE_PRIORITY     |
      | BATCH_DEL_002 | EUR      | 82000.00 | SEPA_INSTANT_CLEARING |
      | BATCH_DEL_003 | INR      | 95000.00 | RBI_RTGS_SETTLEMENT   |
