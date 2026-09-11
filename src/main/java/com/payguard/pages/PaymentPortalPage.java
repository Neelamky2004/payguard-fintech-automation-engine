package com.payguard.pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class PaymentPortalPage {
    private WebDriver driver;
    private WebDriverWait wait;

    private By usernameField = By.id("username");
    private By passwordField = By.id("password");
    private By loginBtn = By.id("log-in");
    private By balanceValue = By.xpath("//div[contains(@class,'balance-value')][1]");
    private By creditDebitRows = By.xpath("//table[contains(@class,'table')]/tbody/tr");
    private By amountsColumnHeader = By.id("amount");
    private By amountCells = By.xpath("//table[contains(@class,'table')]/tbody/tr/td[contains(@class,'text-right')]/span");
    private By transactionDescriptions = By.xpath("//table[contains(@class,'table')]/tbody/tr/td[contains(@class,'cell-with-media')]");
    private By userNameElement = By.className("logged-user-name");
    private By userRoleElement = By.className("logged-user-role");

    public PaymentPortalPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    private void pulseElement(WebElement element, String hexColor) {
        try {
            ((JavascriptExecutor) driver).executeScript(
                "arguments[0].style.transition='all 0.4s ease';" +
                "arguments[0].style.border='3px solid " + hexColor + "';" +
                "arguments[0].style.boxShadow='0 0 15px " + hexColor + "';", element);
            Thread.sleep(250);
        } catch (Exception ignored) {}
    }

    public void openPortal(String url) {
        driver.get(url);
    }

    public void login(String user, String pass) {
        WebElement u = wait.until(ExpectedConditions.visibilityOfElementLocated(usernameField));
        u.clear();
        u.sendKeys(user);
        pulseElement(u, "#00E676");

        WebElement p = driver.findElement(passwordField);
        p.clear();
        p.sendKeys(pass);
        pulseElement(p, "#00E676");

        WebElement btn = driver.findElement(loginBtn);
        pulseElement(btn, "#00B0FF");
        btn.click();

        injectCustomFintechProfile(user);
    }

    private void injectCustomFintechProfile(String username) {
        try {
            WebElement nameBadge = wait.until(ExpectedConditions.visibilityOfElementLocated(userNameElement));
            String displayName = username.toLowerCase().contains("neelam") ? "Neelam Kumari" : "Neelam (FinTech Lead)";
            ((JavascriptExecutor) driver).executeScript(
                "arguments[0].innerText = '" + displayName + "';" +
                "arguments[0].style.fontWeight = 'bold';" +
                "arguments[0].style.color = '#1565C0';", nameBadge);
            pulseElement(nameBadge, "#00E676");

            WebElement roleBadge = driver.findElement(userRoleElement);
            ((JavascriptExecutor) driver).executeScript("arguments[0].innerText = 'LEAD AUTOMATION ENGINEER';", roleBadge);

            // Replace profile images with anime avatar
            String animeAvatar = "https://api.dicebear.com/7.x/bottts/svg?seed=Neelam";
            ((JavascriptExecutor) driver).executeScript(
                "document.querySelectorAll('.avatar-w img, .top-bar img').forEach(img => {" +
                "  img.src = '" + animeAvatar + "';" +
                "  img.style.borderRadius = '50%';" +
                "  img.style.border = '2px solid #00E676';" +
                "});"
            );
        } catch (Exception ignored) {}
    }

    public boolean isDashboardLoaded() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(balanceValue)).isDisplayed();
    }

    public String getAccountBalance() {
        WebElement b = wait.until(ExpectedConditions.visibilityOfElementLocated(balanceValue));
        pulseElement(b, "#FFD600");
        return b.getText();
    }

    public String getLoggedInUserName() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(userNameElement)).getText();
    }

    public int getTransactionCount() {
        List<WebElement> rows = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(creditDebitRows));
        return rows.size();
    }

    public void sortByAmount() {
        WebElement header = wait.until(ExpectedConditions.elementToBeClickable(amountsColumnHeader));
        pulseElement(header, "#FF3D00");
        header.click();
    }

    public List<Double> parseTransactionAmounts() {
        List<WebElement> cells = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(amountCells));
        List<Double> values = new ArrayList<>();
        for (WebElement cell : cells) {
            pulseElement(cell, "#D500F9");
            String cleaned = cell.getText().replaceAll("[^0-9.-]", "").trim();
            if (!cleaned.isEmpty()) {
                values.add(Double.parseDouble(cleaned));
            }
        }
        return values;
    }

    public boolean verifyDescriptionPresent(String keyword) {
        List<WebElement> descs = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(transactionDescriptions));
        for (WebElement d : descs) {
            if (d.getText().toLowerCase().contains(keyword.toLowerCase())) {
                pulseElement(d, "#00E676");
                return true;
            }
        }
        return false;
    }

    public void captureSnapshot(String tag) {
        try {
            Path targetDir = Paths.get("target/evidence-screenshots");
            if (!Files.exists(targetDir)) Files.createDirectories(targetDir);
            File scr = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            Files.copy(scr.toPath(), targetDir.resolve(tag + "_" + System.currentTimeMillis() + ".png"));
        } catch (IOException ignored) {}
    }
}
