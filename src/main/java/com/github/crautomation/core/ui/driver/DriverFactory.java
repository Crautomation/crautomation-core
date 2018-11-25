package com.github.crautomation.core.ui.driver;

import com.github.crautomation.core.common.properties.SystemProperty;
import com.github.crautomation.core.ui.driver.constants.SupportedBrowsers;
import com.github.crautomation.core.ui.driver.drivers.ChromeImpl;
import com.github.crautomation.core.ui.driver.drivers.FirefoxImpl;
import org.openqa.selenium.WebDriver;

/**
 * Determines which browser is being used and sets the driver accordingly.
 */
public class DriverFactory
{
    public synchronized WebDriver generateDriver()
    {
        final SupportedBrowsers browser = SupportedBrowsers.valueOf(SystemProperty.BROWSER.getValue().toUpperCase());

        WebDriver driver;

        switch (browser)
        {
            case CHROME:
                final ChromeImpl chrome = new ChromeImpl();
                driver = chrome.getDriver();
                break;

            case FIREFOX:
                final FirefoxImpl firefox = new FirefoxImpl();
                driver = firefox.getDriver();
                break;

            default:
                throw new IllegalArgumentException(String.format("No driver created for [%s]" +
                        ". Type not recognised, unable to continue.", browser.toString()));
        }

        return driver;
    }
}
