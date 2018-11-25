package com.github.crautomation.core.ui.driver.drivers;

import com.github.crautomation.core.common.properties.constants.CustomResources;
import com.github.crautomation.core.common.testplatform.TestPlatform;
import com.github.crautomation.core.ui.driver.DriverBase;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxDriverLogLevel;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.support.ThreadGuard;

import java.util.Objects;
import java.util.logging.Level;

/**
 * Implementation for the Firefox GeckoDriver
 */
public class FirefoxImpl extends DriverBase
{
    private WebDriver driver;

    public FirefoxImpl()
    {
        generateDriverConfig();
    }

    /**
     * Determines the running platform and assigns a FirefoxDriver/RemoteWebDriver with the appropriate FirefoxOptions
     */
    protected void generateDriverConfig()
    {
        suppressDriverOutputs();

        if(TestPlatform.isGrid())
        {
            driver = ThreadGuard.protect(Objects.requireNonNull(RemoteDriverImpl.getRemoteWebDriver(setBrowserCapabilities())));
        }
        else
        {
            setupDriverConfig();
            driver = ThreadGuard.protect(new FirefoxDriver(setBrowserCapabilities()));
        }
    }

    public WebDriver getDriver() {
        return driver;
    }

    /**
     * Sets the browser specific configurations
     *
     * @return FirefoxOptions object containing browser configurations
     */
    protected FirefoxOptions setBrowserCapabilities() {

        final FirefoxOptions firefoxOptions = new FirefoxOptions();

        firefoxOptions.setLogLevel(FirefoxDriverLogLevel.fromLevel(Level.OFF));

        return firefoxOptions;
    }

    /**
     * Sets the system property for the local geckodriver.exe path
     */
    protected void setupDriverConfig() {
        System.setProperty(CustomResources.FIREFOX_DRIVER_PROPERTY, CustomResources.FIREFOX_DRIVER_PATH);
    }

    /**
     * Suppresses the Firefox/Marionette outputs in the console during test execution.
     */
    protected void suppressDriverOutputs()
    {
        System.setProperty(FirefoxDriver.SystemProperty.BROWSER_LOGFILE, "/dev/null");
    }
}