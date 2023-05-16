package com.github.crautomation.core.ui.listeners;

import com.github.crautomation.core.common.util.ProminentStep;
import com.github.crautomation.core.ui.test.BaseUITestCase;
import io.qameta.allure.Attachment;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.TestListenerAdapter;

/**
 * ScreenshotListener
 * <p>
 * Manages the screenshot capture and attaches to the Allure Report.
 */
public class ScreenshotListener extends TestListenerAdapter {
    @Override
    public synchronized void onTestFailure(final ITestResult failingTest) {
        final Logger logger = LogManager.getLogger();

        try {
            logger.debug(String.format("Attempting to attach screenshot for failing test %s", failingTest.getName()));
            takeScreenshotAndAttachToReport(((BaseUITestCase) failingTest.getInstance()).getDriver());
        } catch (final ClassCastException ex) {
            logger.info("Unable to attach screenshot, unable to cast the WebDriver class", ex);
        }
    }

    /**
     * Returns the screenshot as a byte array which in turn is attached to the Allure report
     *
     * @param driver current driver instance
     * @return screenshot of a byte array
     */
    @Attachment(value = "Failure Screenshot")
    private byte[] takeScreenshotAndAttachToReport(final WebDriver driver) {
        ProminentStep.create("Attaching Screenshot to Report");

        return ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
    }
}