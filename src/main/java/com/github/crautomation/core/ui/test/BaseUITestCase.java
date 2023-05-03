package com.github.crautomation.core.ui.test;

import com.github.crautomation.core.common.listeners.LoggerListener;
import com.github.crautomation.core.common.listeners.TestOutputListener;
import com.github.crautomation.core.common.test.BaseTest;
import com.github.crautomation.core.ui.driver.DriverFactory;
import com.github.crautomation.core.ui.listeners.DriverEventListener;
import com.github.crautomation.core.ui.listeners.ScreenshotListener;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Listeners;

import java.util.concurrent.TimeUnit;
import java.util.logging.Level;

import static org.hamcrest.MatcherAssert.assertThat;

/**
 * <p>Base UI Test Case</p>
 *
 * Class that manages the core actions at runtime for each UI Test.
 * Contains controllers around the WebDriver, Logging, Screenshots and reporting.
 */
@Listeners({TestOutputListener.class, LoggerListener.class, ScreenshotListener.class})
public class BaseUITestCase extends BaseTest
{
    private static final ThreadLocal<WebDriver> threadLocalDriver = ThreadLocal.withInitial(() -> null);

    private static final DriverFactory driverFactory = new DriverFactory();

    private static EventFiringWebDriver eDriver = null;

    private static DriverEventListener eventListener;

    @BeforeSuite(alwaysRun = true)
    protected void preSuite()
    {
        java.util.logging.Logger.getLogger("org.openqa.selenium.remote").setLevel(Level.SEVERE);
    }


    /**
     * Generates the appropriate WebDriver and feeds it into a ThreadSafe driver
     * before test method execution.
     */
    @Override
    @BeforeMethod(alwaysRun = true)
    protected void setup()
    {
        generateWebDriver();

        setImplicitTimeout();

        super.setup();
    }

    /**
     * Attempts to clear the WebDriver and ThreadLocal instances from memory.
     */
    @AfterMethod(alwaysRun = true)
    protected void tearDown()
    {
        final WebDriver driver = getDriver();

        eDriver.unregister(eventListener);

        if (driver != null)
        {
            driver.quit();
        }

        try {
            threadLocalDriver.remove();
        }
        catch (final Exception e)
        {
            final Logger log = LogManager.getLogger();
            log.debug("Unable to remove driver object from ThreadLocal", e);
        }
    }

    /**
     * Creates an initial ThreadSafe, WebDriver instance based on the Capabilities defined in the
     * DriverFactory
     */
    private void generateWebDriver()
    {
        try {
            eDriver = new EventFiringWebDriver(driverFactory.generateDriver());

            eventListener = new DriverEventListener();

            eDriver.register(eventListener);

            threadLocalDriver.set(eDriver);

        } catch (final Exception e)
        {
            e.printStackTrace();
            assertThat("Driver generation problem encountered", false);
        }
    }

    /**
     * Sets the implicit time out to the driver on test setup.
     */
    private void setImplicitTimeout()
    {
        getDriver().manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    }

    /**
     * Returns the current EventFiringWebDriver instance.
     *
     * @return EventFiringWebDriver object
     */
    public WebDriver getDriver()
    {
        return threadLocalDriver.get();
    }

    /**
     * Static return of the current threadLocalDriver
     */
    public static WebDriver getWebDriver()
    {
        return threadLocalDriver.get();
    }
}
