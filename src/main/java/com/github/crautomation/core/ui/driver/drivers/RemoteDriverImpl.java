package com.github.crautomation.core.ui.driver.drivers;

import com.github.crautomation.core.common.test.SeleniumNode;
import com.github.crautomation.core.common.testplatform.constants.TestPlatforms;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;

import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Returns a RemoteWebDriver instance with the provided Capabilities for running against Selenium Grid.
 */
class RemoteDriverImpl {
    /**
     * Returns a RemoteWebDriver object with the defined capabilities attached.
     */
    static RemoteWebDriver getRemoteWebDriver(final Capabilities capabilities) {
        final Logger log = LogManager.getLogger();

        try {
            final RemoteWebDriver remoteWebDriver =
                    new RemoteWebDriver(new URL(TestPlatforms.GRID.getUrl()), capabilities);

            log.trace("Selenium Grid Node ["
                    + SeleniumNode.getConnectionDetails(remoteWebDriver) + "]");

            return remoteWebDriver;
        } catch (final MalformedURLException e) {
            assertThat("Selenium hub address is in an unrecognised format, exception caught:" + e.toString(), false);
        }

        return null;
    }
}
