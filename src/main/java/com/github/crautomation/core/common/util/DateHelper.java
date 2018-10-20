package com.github.crautomation.core.common.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

/**
 * Utility Class for Date or Time related utility methods.
 */
public class DateHelper
{
    /**
     * Returns the current time in the format provided.
     *
     * @param pattern ISO 8601 Notation
     * @return current time in provided format
     */
    public static String getCurrentTime(final String pattern)
    {
        final Logger logger = LogManager.getLogger();
        logger.debug(String.format(("Returning the current time using the ISO 8601 Notation format: %s"), pattern));

        final LocalTime time = LocalTime.now();

        final DateTimeFormatter formatter =
                DateTimeFormatter.ofPattern(pattern, Locale.UK);

        return time.format(formatter);
    }
}
