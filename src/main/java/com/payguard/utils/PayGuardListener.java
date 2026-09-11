package com.payguard.utils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.payguard.base.BaseTest;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.ISuite;
import org.testng.ISuiteListener;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class PayGuardListener implements ITestListener, ISuiteListener {
    private static ExtentReports extent;

    @Override
    public void onStart(ISuite suite) {
        extent = ExtentReportManager.getInstance();
    }

    @Override
    public void onTestStart(ITestResult result) {
        if (extent == null) extent = ExtentReportManager.getInstance();
        ExtentTest test = extent.createTest(result.getMethod().getMethodName(), result.getMethod().getDescription());
        test.assignCategory(result.getTestClass().getRealClass().getSimpleName());
        ExtentReportManager.setTest(test);
        test.log(Status.INFO, "Execution initiated for: " + result.getMethod().getDescription());
        System.out.println("[EXECUTING]: " + result.getMethod().getDescription());
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        ExtentTest test = ExtentReportManager.getTest();
        if (test != null) {
            test.log(Status.PASS, "Validation SLA verified successfully.");
            attachScreenshot(test, "Evidence_Pass");
        }
        System.out.println("  --> [PASSED - SLA OK]: " + result.getName());
    }

    @Override
    public void onTestFailure(ITestResult result) {
        ExtentTest test = ExtentReportManager.getTest();
        if (test != null) {
            test.log(Status.FAIL, "Execution Failed: " + result.getThrowable().getMessage());
            attachScreenshot(test, "Evidence_Failure");
        }
        System.err.println("  --> [FAILED]: " + result.getName());
    }

    private void attachScreenshot(ExtentTest test, String title) {
        try {
            if (BaseTest.getDriver() != null) {
                String base64 = ((TakesScreenshot) BaseTest.getDriver()).getScreenshotAs(OutputType.BASE64);
                test.addScreenCaptureFromBase64String("data:image/png;base64," + base64, title);
            }
        } catch (Exception ignored) {}
    }

    @Override
    public void onFinish(ISuite suite) {
        if (extent != null) extent.flush();
    }
}
