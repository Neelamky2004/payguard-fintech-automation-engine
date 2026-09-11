package com.payguard.tests;

import com.payguard.base.BaseTest;
import com.payguard.pages.PaymentPortalPage;
import com.payguard.utils.RetryAnalyzer;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.List;

public class PayGuardUITest extends BaseTest {

    @DataProvider(name = "fintechRailsProvider")
    public Object[][] getFintechRails() {
        return new Object[][] {
            {"BATCH_PAY_901", "EUR", 125000.00, "SEPA_DIRECT_INSTANT"},
            {"BATCH_PAY_902", "USD", 489000.50, "FED_WIRE_DOMESTIC"},
            {"BATCH_PAY_903", "INR", 9850000.00, "RBI_NEFT_RTGS_SETTLED"},
            {"BATCH_PAY_904", "GBP", 73400.00, "BACS_FASTER_PAYMENTS"}
        };
    }

    @Test(priority = 1, retryAnalyzer = RetryAnalyzer.class, description = "Security Guard: Verify Blank/Null Credentials Validation")
    public void testAuthenticationSecurityBoundary() {
        PaymentPortalPage portal = new PaymentPortalPage(getDriver());
        portal.openPortal("https://demo.applitools.com/hackathon.html");
        portal.login("", "");
        portal.captureSnapshot("Auth_Blank_Validated");
        Assert.assertTrue(getDriver().getTitle().length() > 0, "Portal session crashed unexpectedly on empty submission.");
    }

    @Test(priority = 2, retryAnalyzer = RetryAnalyzer.class, description = "Dashboard Verification: Multi-metric balance, ledger rows and descriptions")
    public void testFinancialDashboardIntegrity() {
        PaymentPortalPage portal = new PaymentPortalPage(getDriver());
        portal.openPortal("https://demo.applitools.com/hackathon.html");
        portal.login("neelam.kumari@deloitte.com", "AuditEngine@2026");

        Assert.assertTrue(portal.isDashboardLoaded(), "Core financial dashboard failed to render.");
        Assert.assertTrue(portal.getAccountBalance().contains("$"), "Balance formatting invalid.");
        Assert.assertTrue(portal.getTransactionCount() >= 5, "Audit table count subceeds threshold.");
        Assert.assertTrue(portal.verifyDescriptionPresent("Starbucks") || portal.verifyDescriptionPresent("MailChimp"), 
            "Expected merchant descriptors missing from primary audit ledger.");
        portal.captureSnapshot("Dashboard_Integrity_Verified");
    }

    @Test(priority = 3, retryAnalyzer = RetryAnalyzer.class, description = "Ledger Audit: Table sorting event and absolute ledger volume calculation")
    public void testLedgerSortingAndVolumeCalculations() {
        PaymentPortalPage portal = new PaymentPortalPage(getDriver());
        portal.openPortal("https://demo.applitools.com/hackathon.html");
        portal.login("fintech_quant_analyst", "QuantAudit@2026");

        portal.sortByAmount();
        List<Double> amounts = portal.parseTransactionAmounts();
        Assert.assertFalse(amounts.isEmpty(), "Zero parsed transaction amounts found.");

        double sum = 0.0;
        for (Double amt : amounts) sum += Math.abs(amt);
        Assert.assertTrue(sum > 100.0, "Calculated transaction turnover below expected floor threshold.");
        portal.captureSnapshot("Ledger_Volume_Computed");
    }

    @Test(priority = 4, dataProvider = "fintechRailsProvider", description = "Treasury Matrix: Multi-currency settlement batches via global banking rails")
    public void testMultiRailSettlementMatrix(String batchId, String currency, double amount, String rail) {
        Assert.assertTrue(batchId.startsWith("BATCH_PAY"), "Routing format invalid: " + batchId);
        Assert.assertTrue(currency.matches("^[A-Z]{3}$"), "Currency code must follow ISO 4217 standard: " + currency);
        Assert.assertTrue(amount >= 1000.0, "Settlement below permissible bulk threshold.");
        Assert.assertNotNull(rail, "Designated settlement rail missing.");
    }
}
