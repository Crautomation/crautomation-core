package com.github.crautomation.core.ui.util;

import com.github.crautomation.core.common.properties.constants.CustomResources;
import com.google.common.base.Charsets;
import com.google.common.io.Resources;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;

import java.io.IOException;
import java.net.URL;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * <p>SizzleSelector</p>
 *
 * Injects query Sizzle selector to the browser under test. Sizzle allows the mapping of WebElements
 * utilising jquery commands such as:
 *
 * :contains("  ")
 * :nth-value(" ")
 * :first
 * :last
 */
@SuppressWarnings("unchecked")
public class SizzleSelector {

    private JavascriptExecutor driver;
    private final Logger logger = LogManager.getLogger();

    public SizzleSelector(final WebDriver webDriver) {
        driver = (JavascriptExecutor) webDriver;
    }

    /**
     * Find element by sizzle css.
     *
     * @param cssLocator the cssLocator
     * @return the web element
     */
    public WebElement findElementBySizzleCss(final String cssLocator) {
        final List<WebElement> elements = findElementsBySizzleCss(cssLocator);

        if (elements != null && elements.size() > 0) {
            return elements.get(0);
        }

        // If code reaches here it was unable to find the element with the CSS Selector
        throw new NoSuchElementException("ERROR! Selector '" + cssLocator
                + "' cannot be found in DOM");
    }

    /**
     * Find elements by sizzle css.
     *
     * @param cssLocator the cssLocator
     * @return the list of the web elements that match this locator
     */
    public List<WebElement> findElementsBySizzleCss(final String cssLocator) {
        injectSizzleIfNeeded();

        final String javascriptExpression = createSizzleSelectorExpression(cssLocator);

        return executeRemoteScript(javascriptExpression);
    }

    private String createSizzleSelectorExpression(final String using)
    {
        return "return Sizzle(\"" + using + "\")";
    }

    private List<WebElement> executeRemoteScript(final String javascriptExpression)
    {
        List<WebElement> list = null;
        JavascriptExecutor executor = driver;

        try {
            list = (List<WebElement>) executor
                    .executeScript(javascriptExpression);
        } catch (WebDriverException wde) {
            if (wde.getMessage().contains("Sizzle is not defined")) {
                logger.trace(
                        "Attempt to execute the code '"
                                + javascriptExpression
                                + "' has failed - Sizzle was not detected. Trying once more",
                        wde);
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e)
                {
                    logger.debug("Error:", e);
                }
                // Try to inject sizzle once more.
                injectSizzleIfNeeded();
                // Trying again to execute
                list = (List<WebElement>) executor
                        .executeScript(javascriptExpression);
            } else {
                throw wde;
            }
        } finally {
            if (list == null) {
                list = Collections.emptyList();
            }
        }
        return list;
    }

    /**
     * Inject sizzle if needed.
     */
    private void injectSizzleIfNeeded()
    {
        if (!sizzleLoaded()) {
            injectSizzle();
        } else {
            // Sizzle has loaded
            return;
        }

        for (int i = 0; i < 20; i++) {
            if (sizzleLoaded()) {
                // Sizzle has loaded
                return;
            }
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                logger.debug("Exception caught: ", e);
            }
            if (i % 10 == 0) {
                logger.debug("Attempting to re-load SizzleCSS from local directory");
                injectSizzle();
            }
        }

        // Try on last time
        if (!sizzleLoaded()) {
            logger.debug("Sizzle was not loaded despite multiple attempts.");
        }
        // Sizzle did not load correctly.
        throw new RuntimeException(
                "Able to load Sizzle into the browser.");
    }

    /**
     * Check if the Sizzle library is loaded.
     *
     * @return the true if Sizzle is loaded in the web page
     */
    private Boolean sizzleLoaded()
    {
        Boolean loaded = true;

        try {
            loaded = (Boolean) driver
                    .executeScript("return (window.Sizzle != null);");

        } catch (WebDriverException e) {
            logger.debug(
                    "Whilst trying to verify Sizzle has loaded, WebDriver threw an exception",
                    e);
            loaded = false;
        }

        return loaded;
    }

    /**
     * Injects Sizzle from a local file.
     */
    private void injectSizzle()
    {
        String scriptContent = null;

        try {
            URL sizzleFile = Resources.getResource(CustomResources.SIZZLE_FILE_PATH);
            scriptContent = Resources.toString(sizzleFile, Charsets.UTF_8);
        } catch (IOException e) {
            logger.debug("Unable to read local sizzle file", e);
        }
        String execulatbleScript = " var headID = document.getElementsByTagName(\"head\")[0];"
                + "var newScript = document.createElement('script');"
                + "newScript.type = 'text/javascript';";
        if (scriptContent != null) {
            execulatbleScript = execulatbleScript + "newScript.text = "
                    + scriptContent;
        } else {
            execulatbleScript = execulatbleScript
                    + "newScript.src = 'https://raw.githubusercontent.com/jquery/sizzle/master/src/sizzle.js';";
        }
        execulatbleScript = execulatbleScript
                + "headID.appendChild(newScript);";

        logger.trace(String.format("Injecting Sizzle with the following script: %s", execulatbleScript));

        driver.executeScript(execulatbleScript);
    }
}