package com.github.crautomation.core.common.test;

import com.github.crautomation.core.common.LoggerFactory;
import com.github.crautomation.core.common.properties.SystemProperty;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.config.Configurator;
import org.junit.Before;
import org.testng.annotations.BeforeMethod;

/**
 * Superclass that forms the basis of all TestNG and unit tests
 */
public class BaseTest {
    final protected Logger log = LogManager.getLogger();
    private String loggerFileName;

    @Before
    public void suppressUnitTestLogging() {
        setRootLoggingLevel(Level.OFF);
    }

    @BeforeMethod(alwaysRun = true)
    protected void setup() {
        setLoggerFileName();

        setRootLoggingLevel(LoggerFactory.getTestLogLevel(SystemProperty.LOG_LEVEL.getValue()));
    }

    /**
     * Get Logger file name
     *
     * @return String logfile name for currently executing thread
     */
    public String getLoggerFileName() {
        return loggerFileName;
    }

    /**
     * Sets the root logging level to the level provided.
     *
     * @param level - Logging level
     */
    private void setRootLoggingLevel(final Level level) {
        Configurator.setRootLevel(level);
    }

    /**
     * Sets the name of the logfile for the current executing thread.
     */
    private void setLoggerFileName() {
        loggerFileName = new LoggerFactory(getClass().getName()).setupThreadLogger();
    }
}

