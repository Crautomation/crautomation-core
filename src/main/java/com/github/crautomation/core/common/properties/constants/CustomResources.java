package com.github.crautomation.core.common.properties.constants;

import static com.github.crautomation.core.common.properties.SystemProperty.PROJECT_BUILD_DIRECTORY;

/**
 * System Wide CustomResources
 */
public class CustomResources
{
    // Chrome Browser
    public static final String CHROME_DRIVER_PROPERTY = "webdriver.chrome.driver";
    public static final String CHROME_DRIVER_PATH = "C:\\AutomationDrivers\\chromedriver.exe";

    // Firefox Browser
    public static final String FIREFOX_DRIVER_PROPERTY = "webdriver.gecko.driver";
    public static final String FIREFOX_DRIVER_PATH = "C:\\AutomationDrivers\\geckodriver.exe";

    // SizzleSelector
    public static final String SIZZLE_FILE_PATH = "sizzle.min.js";

    // Logging
    public static final String LOG_LOCATION = PROJECT_BUILD_DIRECTORY.getValue().concat("/logs/");
}
