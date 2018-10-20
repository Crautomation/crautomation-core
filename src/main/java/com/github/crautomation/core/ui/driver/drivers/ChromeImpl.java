package com.github.crautomation.core.ui.driver.drivers;

import com.github.crautomation.core.common.properties.constants.CustomResources;
import com.github.crautomation.core.common.testplatform.TestPlatform;
import com.github.crautomation.core.ui.driver.DriverBase;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ThreadGuard;

import java.util.Objects;

/**
 * Implementation for the ChromeDriver
 */
public class ChromeImpl extends DriverBase
{
    private WebDriver driver;

    public ChromeImpl()
    {
        generateDriverConfig();
    }

    /**
     * Determines the running platform and assigns a ChromeDriver/RemoteWebDriver with the appropriate ChromeOptions
     */
    @Override
    protected void generateDriverConfig()
    {
        suppressChromeOutputs();

        if(TestPlatform.isGrid())
        {
            driver = ThreadGuard.protect(Objects.requireNonNull(RemoteDriverImpl.getRemoteWebDriver(setBrowserCapabilities())));
        }
        else
        {
            setDriverPath();
            driver = ThreadGuard.protect(new ChromeDriver(setBrowserCapabilities()));
        }
    }

    public WebDriver getDriver() {
        return driver;
    }

    /**
     * Sets the browser specific configurations
     *
     * @return ChromeOptions object containing browser configurations
     */
    @Override
    protected ChromeOptions setBrowserCapabilities() {

        final ChromeOptions chromeOptions = new ChromeOptions();

        chromeOptions.setCapability("acceptSslCerts", true);
        chromeOptions.setCapability("takeScreenshot", true);
        chromeOptions.setCapability("cssSelectorsEnabled", true);
        chromeOptions.setCapability("chrome.switches", "--no-default-browser-check");

        return chromeOptions;
    }

    /**
     * Sets the system property for the local ChromeDriver.exe path
     */
    @Override
    protected void setDriverPath()
    {
        System.setProperty(CustomResources.CHROME_DRIVER_PROPERTY, CustomResources.CHROME_DRIVER_PATH);
    }


    /**
     * Suppresses the Chrome and Selenium outputs in the console during test execution.
     */
    private void suppressChromeOutputs()
    {
        System.setProperty("webdriver.chrome.silentOutput", "true");
    }
}
