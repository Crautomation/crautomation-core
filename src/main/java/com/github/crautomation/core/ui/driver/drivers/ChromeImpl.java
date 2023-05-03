package com.github.crautomation.core.ui.driver.drivers;

import com.github.crautomation.core.common.properties.constants.CustomResources;
import com.github.crautomation.core.common.testplatform.TestPlatform;
import com.github.crautomation.core.common.util.PropertiesReader;
import com.github.crautomation.core.ui.driver.DriverBase;
import com.github.crautomation.core.ui.driver.constants.OperatingSystem;
import com.github.crautomation.core.ui.util.OS;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ThreadGuard;

import java.io.File;
import java.util.Objects;

import static org.hamcrest.MatcherAssert.assertThat;

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
    protected void generateDriverConfig()
    {
        suppressDriverOutputs();

        if(!TestPlatform.getTestPlatform().getUrl().isEmpty())
        {
            driver = ThreadGuard.protect(Objects.requireNonNull(RemoteDriverImpl.getRemoteWebDriver(setBrowserCapabilities())));
        }
        else
        {
            setupDriverConfig();
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
    protected ChromeOptions setBrowserCapabilities() {

        final ChromeOptions chromeOptions = new ChromeOptions();

//        chromeOptions.setCapability("acceptSslCerts", true);
//        chromeOptions.setCapability("takeScreenshot", true);
//        chromeOptions.setCapability("cssSelectorsEnabled", true);
//        chromeOptions.setCapability("chrome.switches", "--no-default-browser-check");

        return chromeOptions;
    }

    /**
     * Sets the system property for the local chromedriver.exe path
     */
    protected void setupDriverConfig()
    {
        final OperatingSystem os = OS.determine();
        String completeDriverPath = "drivers/chromedriver_";

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

        System.setProperty("webdriver.chrome.driver", file.toString());
    }


    /**
     * Suppresses the Chrome outputs in the console during test execution.
     */
    protected void suppressDriverOutputs()
    {
        System.setProperty("webdriver.chrome.silentOutput", "true");
    }
}
