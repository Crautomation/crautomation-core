package com.github.crautomation.core.common.properties;

import com.github.crautomation.core.common.testplatform.constants.TestPlatforms;
import com.github.crautomation.core.ui.driver.constants.SupportedBrowsers;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

/**
 * Contains all the System Properties utilised in the framework, anything without a default value has a null value
 * which will fail runs without appropriate values set e.g. project.builddir
 */
public enum SystemProperty {
    // Runtime Arguments
    PROJECT_BUILD_DIRECTORY("project.builddir", System.getProperty("user.dir").concat("/target")),
    BROWSER("browser", SupportedBrowsers.CHROME.toString()),
    TEST_PLATFORM("test.platform", TestPlatforms.LOCAL.getName()),
    LOG_LEVEL("log.level", "INFO");

    private String value;
    private String propertyName;

    SystemProperty(final String key, final String def) {
        propertyName = key;

        if (def == null) {
            this.value = System.getProperty(key);
        } else {
            this.value = System.getProperty(key, def);
        }
    }

    /**
     * Returns the requested 'System Property'
     *
     * @return String value system property
     */
    public String getValue() {
        validateProperty();

        final Logger log = LogManager.getLogger();
        log.debug(String.format("Returning the System Property: %s as %s", propertyName, value));

        return value;
    }

    /**
     * Asserts that the System Property is not an empty or null value
     */
    private void validateProperty() {
        assertThat(String.format("System Property -D%s is null, have you set the property?", propertyName), value, is(notNullValue()));
        assertThat(String.format("System Property -D%s is empty, have you set the property?", propertyName), value.isEmpty(), is(false));
    }
}
