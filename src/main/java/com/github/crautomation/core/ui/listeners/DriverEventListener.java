package com.github.crautomation.core.ui.listeners;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.events.WebDriverEventListener;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Listener registers to a EventFiringWebDriver instance and records TRACE
 * level logging for the events that are fired.
 */
public class DriverEventListener implements WebDriverEventListener
{
    private final Logger log = LogManager.getLogger();

    @Override
    public void beforeAlertAccept(final WebDriver webDriver) {
        log.trace("Attempting to accept the alert.");
    }

    @Override
    public void afterAlertAccept(final WebDriver webDriver) {
        log.trace("Successfully accepted the alert.");
    }

    @Override
    public void afterAlertDismiss(final WebDriver webDriver) {
        log.trace("Successfully to dismissed the alert.");
    }

    @Override
    public void beforeAlertDismiss(final WebDriver webDriver) {
        log.trace("Attempting to dismiss the alert.");
    }

    @Override
    public void beforeNavigateTo(final String url, final WebDriver webDriver)
    {
        log.trace("Attempting to navigate to url: " + url);
    }

    @Override
    public void afterNavigateTo(final String url, final WebDriver webDriver)
    {
        log.trace("Successfully navigated to url: " + url);
    }

    @Override
    public void beforeNavigateBack(final WebDriver webDriver)
    {
        log.trace("Attempting to navigate back.");
    }

    @Override
    public void afterNavigateBack(final WebDriver webDriver)
    {
        log.trace("Successfully navigated back.");
    }

    @Override
    public void beforeNavigateForward(final WebDriver webDriver)
    {
        log.trace("Attempting to navigate forward.");
    }

    @Override
    public void afterNavigateForward(final WebDriver webDriver)
    {
        log.trace("Successfully navigated forward.");
    }

    @Override
    public void beforeNavigateRefresh(final WebDriver webDriver)
    {
        log.trace("Attempting to refresh the page.");
    }

    @Override
    public void afterNavigateRefresh(final WebDriver webDriver)
    {
        log.trace("Successfully refreshed the page.");
    }

    @Override
    public void beforeFindBy(final By by, final WebElement webElement, final WebDriver webDriver)
    {
        log.trace("Attempting to find element: " + by);
    }

    @Override
    public void afterFindBy(final By by, final WebElement webElement, final WebDriver webDriver)
    {
        log.trace("Successfully found element: " + by);
    }

    @Override
    public void beforeClickOn(final WebElement webElement, final WebDriver webDriver)
    {
        log.trace("Attempting to click element: " + getLocatorFromElement(webElement));
    }

    @Override
    public void afterClickOn(final WebElement webElement, final WebDriver webDriver)
    {
        log.trace("Successfully clicked element: " + getLocatorFromElement(webElement));
    }

    @Override
    public void beforeChangeValueOf(final WebElement webElement, final WebDriver webDriver, final CharSequence[] charSequences) {
        log.trace("Attempting to change the value of " + webElement.getTagName());
    }

    @Override
    public void afterChangeValueOf(final WebElement webElement, final WebDriver webDriver, final CharSequence[] charSequences) {
        log.trace("Successfully changed the value of " + webElement.getTagName());
    }

    @Override
    public void beforeScript(final String script, final WebDriver webDriver)
    {
        log.trace("Starting script: " + script);
    }

    @Override
    public void afterScript(final String script, final WebDriver webDriver)
    {
        log.trace("Completed script: " + script);
    }

    @Override
    public void beforeSwitchToWindow(final String s, final WebDriver webDriver) {
        log.trace("Attempting to switch to window: " + s);
    }

    @Override
    public void afterSwitchToWindow(final String s, final WebDriver webDriver) {
        log.trace("Successfully switched to window: " + s);
    }

    @Override
    public void onException(final Throwable throwable, final WebDriver webDriver)
    {
        log.trace("Exception thrown: " + throwable);
    }

    @Override
    public <X> void beforeGetScreenshotAs(final OutputType<X> outputType) {
        log.trace("Attempting to take a screenshot.");
    }

    @Override
    public <X> void afterGetScreenshotAs(final OutputType<X> outputType, X x) {
        log.trace("Successfully taken a screenshot.");
    }

    @Override
    public void beforeGetText(final WebElement webElement, WebDriver webDriver) {
        log.trace("Attempting to get the text: " + webElement.getText());
    }

    @Override
    public void afterGetText(final WebElement webElement, WebDriver webDriver, String s) {
        log.trace("Successfully to got the text: " + webElement.getText());
    }

    private String getLocatorFromElement(final WebElement element)
    {
        final String elementString = element.toString();
        final Pattern pattern = Pattern.compile("->\\s(.*)(?=\\])");
        final Matcher matcher = pattern.matcher(elementString);

        return matcher.find() && matcher.groupCount() > 0 ? matcher.group(1) : elementString;
    }
}
