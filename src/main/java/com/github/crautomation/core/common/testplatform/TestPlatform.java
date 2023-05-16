package com.github.crautomation.core.common.testplatform;

import com.github.crautomation.core.common.properties.SystemProperty;
import com.github.crautomation.core.common.testplatform.constants.TestPlatforms;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Contains methods for identifying which platform the tests are running against.
 */
public class TestPlatform {
    /**
     * Confirms if the current tests are running against the Selenium Grid.
     *
     * @return true/false
     */
    public static boolean isGrid() {
        return SystemProperty.TEST_PLATFORM.getValue().equalsIgnoreCase(TestPlatforms.GRID.getName());
    }

    /**
     * Checks to see if the current tests are running against Local.
     *
     * @return true/false
     */
    public static boolean isLocal() {
        return SystemProperty.TEST_PLATFORM.getValue().equalsIgnoreCase(TestPlatforms.LOCAL.getName());
    }

    /**
     * Determines the current platform the tests are running against.
     *
     * @return TestPlatforms.LOCAL/TestPlatforms.GRID
     */
    public static TestPlatforms getTestPlatform() {
        final Logger logger = LogManager.getLogger();

        if (isGrid()) {
            logger.debug("Current test platform: Selenium Grid");
            return TestPlatforms.GRID;
        } else {
            logger.debug("Current test platform: Local");
            return TestPlatforms.LOCAL;
        }
    }
}
