package com.github.crautomation.core.ui.driver.drivers;

import com.github.crautomation.core.common.properties.constants.CustomResources;
import com.github.crautomation.core.common.testplatform.TestPlatform;
import com.github.crautomation.core.common.util.PropertiesReader;
import com.github.crautomation.core.ui.driver.DriverBase;
import com.github.crautomation.core.ui.driver.constants.OperatingSystem;
import com.github.crautomation.core.ui.util.OS;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxDriverLogLevel;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.support.ThreadGuard;

import java.io.File;
import java.util.Objects;
import java.util.logging.Level;

import static org.hamcrest.MatcherAssert.assertThat;

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
    protected void setupDriverConfig()
    {
        final OperatingSystem os = OS.determine();
        String completeDriverPath = "drivers/geckodriver_";

        switch(os)
        {
            case MAC:
                completeDriverPath = completeDriverPath.concat(OperatingSystem.MAC.toString().toLowerCase());
                break;
            case WINDOWS:
                completeDriverPath = completeDriverPath.concat(OperatingSystem.WINDOWS.toString().toLowerCase().concat(".exe"));
                break;
            default:
                assertThat("Unable to determine current platform, aborting.", false);
        }

        final File file = PropertiesReader.readFile(completeDriverPath);

        System.setProperty("webdriver.gecko.driver", file.toString());
    }

    /**
     * Suppresses the Firefox/Marionette outputs in the console during test execution.
     */
    protected void suppressDriverOutputs()
    {
        System.setProperty(FirefoxDriver.SystemProperty.BROWSER_LOGFILE, "/dev/null");
    }
}