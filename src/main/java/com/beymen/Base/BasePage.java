package com.beymen.Base;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public abstract class BasePage {
    public WebDriver driver;
    public WebDriverWait wait;
    public static final Logger logger = LogManager.getLogger(BasePage.class);

    public BasePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        PageFactory.initElements(driver, this);
    }

    public void waitForElement(WebElement element) {
        wait.until(ExpectedConditions.visibilityOf(element));
    }

    public void waitForElementClickable(WebElement element) {
        wait.until(ExpectedConditions.elementToBeClickable(element));
    }

    public void click(WebElement element) {
        waitForElementClickable(element);
        element.click();
        logger.info("Clicked on element: " + element.toString());
    }

    public void sendKeys(WebElement element, String text) {
        waitForElement(element);
        element.clear();
        element.sendKeys(text);
        logger.info("Entered text: " + text);
    }

    public String getText(WebElement element) {
        waitForElement(element);
        return element.getText();
    }



}
