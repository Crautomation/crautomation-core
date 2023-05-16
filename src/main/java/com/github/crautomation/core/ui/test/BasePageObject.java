package com.github.crautomation.core.ui.test;

import com.github.crautomation.core.ui.util.SizzleSelector;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

/**
 * <p>BasePageObject</p>
 * <p>
 * Class that simplifies Page Object classes. It contains basic driver and logging functionality
 * in addition to common UI interaction methods.
 */
public abstract class BasePageObject<T extends BasePageObject<T>> {
    protected final Logger log = LogManager.getLogger(this);
    protected WebDriver driver;
    protected SizzleSelector sizzle;

    public BasePageObject() {
        this.driver = BaseUITestCase.getWebDriver();

        this.sizzle = new SizzleSelector(this.driver);
    }

    /**
     * Returns the current WebDriver instance
     *
     * @return WebDriver object
     */
    protected WebDriver getDriver() {
        return this.driver;
    }

    /**
     * Returns the current SizzleSelector instance
     *
     * @return SizzleSelector object
     */
    protected SizzleSelector getSizzle() {
        return this.sizzle;
    }

    /**
     * Matches Selectors defined in @FindBy WebElement definitions
     * and maps them to their variable.
     */
    protected void initElements() {
        PageFactory.initElements(driver, this);
    }


    /**
     * Switches to the iFrame with the provided ID
     *
     * @param frameId String frame ID
     */
    protected void switchToFrame(final String frameId) {
        getDriver().switchTo().frame(frameId);

        initElements();
    }

    /**
     * Method required to counteract the changes made in later versions of FF where it doesn't save DOM references on page-reloads.
     */
    protected void reloadDom() {
        getDriver().switchTo().defaultContent();

        initElements();
    }

    /**
     * Periodically checks the page for the expected WebElement
     *
     * @param driver     current WebDriver instance
     * @param webElement element which is expected on the page
     * @return true/false if element becomes visible
     */
    protected boolean waitForElementToBeVisible(final WebDriver driver, final WebElement webElement) {
        initElements();

        try {
            new WebDriverWait(driver, Duration.ofSeconds(30)).pollingEvery(Duration.ofMillis(500))
                    .until(ExpectedConditions.visibilityOf(webElement));
        } catch (Exception e) {
            log.debug(String.format("WebElement %s was not found.", webElement), e);
            return false;
        }

        return true;
    }

    /**
     * Get the current page object. Useful for e.g.
     * <code>myPage.get().then().doSomething();</code>
     *
     * @return the current page object
     */
    @SuppressWarnings("unchecked")
    public T then() {
        return (T) this;
    }

    /**
     * Get current page object. Useful for e.g.
     * <code>myPage.get().then().with().aComponent().clickHome();</code>
     *
     * @return the current page object
     */
    @SuppressWarnings("unchecked")
    public T with() {
        return (T) this;
    }

    @SuppressWarnings("unchecked")
    public T and() {
        return (T) this;
    }
}
