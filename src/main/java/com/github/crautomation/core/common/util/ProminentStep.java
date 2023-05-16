package com.github.crautomation.core.common.util;

import io.qameta.allure.Step;
import org.apache.logging.log4j.LogManager;

/**
 * Adds a prominent (top-level) step to the allure report for the method you call it in.
 */
public class ProminentStep {
    @Step("{step}")
    public static void create(final String step) {
        LogManager.getLogger().debug(String.format("Added prominent step to allure report [%s]", step));
    }
}