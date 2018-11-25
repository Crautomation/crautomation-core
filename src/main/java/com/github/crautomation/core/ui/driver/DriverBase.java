package com.github.crautomation.core.ui.driver;

import org.openqa.selenium.Capabilities;

/**
 * Superclass for all Browser based WebDriver implementations
 */
public abstract class DriverBase
{
    protected abstract void generateDriverConfig();

    protected abstract Capabilities setBrowserCapabilities();

    protected abstract void setupDriverConfig();

    protected abstract void suppressDriverOutputs();
}
