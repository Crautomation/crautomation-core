package com.github.crautomation.core.ui.util;

import com.github.crautomation.core.ui.driver.constants.OperatingSystem;

import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Determines the current platform that the execution is against
 * <p>
 * Returns answer as Platform enum.
 */
public final class OS {
    private static final String CURRENT_PLATFORM = System.getProperty("os.name");

    public static OperatingSystem determine() {
        OperatingSystem platform = null;

        if (CURRENT_PLATFORM.contains("Windows")) {
            platform = OperatingSystem.WINDOWS;
        } else if (CURRENT_PLATFORM.contains("Mac")) {
            platform = OperatingSystem.MAC;
        } else {
            assertThat("Unable to determine current executing platform, aborting.", false);
        }
        return platform;
    }
}