package com.github.crautomation.core.common;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.ThreadContext;

/**
 * Factory class for Log instances.
 * <p>
 * Contains all the functionality for obtaining and setting the desired logging level, setting the logfile name
 * and doing thread-based matching of file -> logger instance.
 **/
public class LoggerFactory {
    private String loggerFileName;
    private String testName;

    public LoggerFactory(final String testName) {
        this.testName = testName;
    }

    /**
     * Matches the input from the runtime argument log.level to an Apache Level.
     *
     * @param logLevel - String value log level
     * @return Level.OFF/INFO/DEBUG/TRACE
     */
    public static Level getTestLogLevel(final String logLevel) {
        Level level = null;

        switch (logLevel.toUpperCase()) {
            case "INFO":
                level = Level.INFO;
                break;
            case "DEBUG":
                level = Level.DEBUG;
                break;
            case "TRACE":
                level = Level.TRACE;
                break;
            default:
                throw new IllegalArgumentException(String.format("Log level: %s does " +
                        "not match the available options, aborting.", logLevel.toUpperCase()));
        }
        return level;
    }

    public String setupThreadLogger() {
        setLoggerFileName();

        setThreadContextName();

        return loggerFileName;
    }

    /**
     * Sets the thread context to match the logger file to the thread
     */
    private void setThreadContextName() {
        ThreadContext.put("threadName", loggerFileName);
    }

    /**
     * Sets the thread-specific Logger file name
     */
    private void setLoggerFileName() {
        loggerFileName = generateFormattedLogFileName();

        final Logger log = LogManager.getLogger();
        log.debug(String.format("Returned the logger file name as: %s", loggerFileName));
    }

    /**
     * Formats the log file name into a more readable format.
     */
    private String generateFormattedLogFileName() {
        String name = testName.replace("com.github.crautomation.", "");

        String threadNumber = Thread.currentThread().getName()
                .replace("TestNG-PoolService-", "Scenario-");

        threadNumber = threadNumber
                .replace("TestNG-test=Surefire test-1", "test");

        return String.format("%s_%s", name, threadNumber);
    }
}