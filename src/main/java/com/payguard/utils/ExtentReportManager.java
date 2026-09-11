package com.payguard.utils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

import java.io.File;

public class ExtentReportManager {
    private static ExtentReports extent;
    private static final ThreadLocal<ExtentTest> testNode = new ThreadLocal<>();

    public static synchronized ExtentReports getInstance() {
        if (extent == null) {
            File reportDir = new File("reports");
            if (!reportDir.exists()) {
                reportDir.mkdirs();
            }

            ExtentSparkReporter spark = new ExtentSparkReporter("reports/PayGuard-Executive-Report.html");
            spark.config().setTheme(Theme.DARK);
            spark.config().setDocumentTitle("PayGuard | Executive Audit Dashboard");
            spark.config().setReportName("Tier-1 FinTech Settlement & Reconciliation Suite");
            spark.config().setTimeStampFormat("EEEE, MMMM dd, yyyy, hh:mm a");

            extent = new ExtentReports();
            extent.attachReporter(spark);
            extent.setSystemInfo("Framework", "PayGuard-Enterprise-Core");
            extent.setSystemInfo("Lead SDET", "Neelam Kumari");
            extent.setSystemInfo("Environment", "Fintech-Staging-01");
            extent.setSystemInfo("Java Version", System.getProperty("java.version"));
            extent.setSystemInfo("OS", System.getProperty("os.name"));
        }
        return extent;
    }

    public static ExtentTest getTest() {
        return testNode.get();
    }

    public static void setTest(ExtentTest test) {
        testNode.set(test);
    }
}
