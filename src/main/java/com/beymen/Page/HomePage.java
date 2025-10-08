package com.beymen.Page;


import com.beymen.Base.BasePage;
import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static java.lang.Thread.sleep;

public class HomePage extends BasePage {


    @FindBy(css = "input.o-header__search--input")
    public WebElement searchInput;

    @FindBy(id = "o-searchSuggestion__input")
    public WebElement searchInput2;

    @FindBy(css = "button.o-header__search--button")
    public WebElement searchButton;

    @FindBy(css = "button.o-header__search--close.-hasButton")
    public WebElement clearButton;

    @FindBy(id = "o-searchSuggestion__input") // veya class / placeholder
    public WebElement Enter;

    @FindBy(css = ".o-header__logo img, .logo img")
    public WebElement logo;

    public HomePage(WebDriver driver) {
        super(driver);
    }

    public boolean isHomePageDisplayed() {
        try {
            waitForElement(logo);
            logger.info("Home page is displayed successfully");
            return logo.isDisplayed();
        } catch (Exception e) {
            logger.error("Home page is not displayed", e);
            return false;
        }
    }

    public void searchProduct(String keyword) {

        searchInput.click();
        searchInput2.click();

        sendKeys(searchInput2, keyword);

        logger.info("Searched for: " + keyword);

    }
    public void searchProduct2(String keyword) {

        WebElement searchInput = driver.findElement(By.id("o-searchSuggestion__input"));
        searchInput.click();
        searchInput.clear();
        searchInput.sendKeys(keyword);

        logger.info("Searched for: " + keyword);
    }

    public void clearSearchBox() {
// Parent button'u bul ve tıkla
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        clearButton.click();

    }

    public SearchPage pressEnter() {
        Enter.click();
        Enter.sendKeys( Keys.ENTER );
        logger.info("Pressed Enter key");
        return new SearchPage(driver);
    }

    public void handleCookiePopup() {
        try {
            Thread.sleep(2000);
            WebElement acceptButton = driver.findElement(By.id("onetrust-accept-btn-handler"));
            acceptButton.click();
            logger.info("Cookie popup accepted");


        } catch (Exception e) {
            logger.info("No cookie popup found or already accepted");
        }
    }
    public void gender() {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

            // Kadın butonu görünür olana kadar bekle
            WebElement womanButton = wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.id("genderWomanButton")));

            // Tıklanabilir olana kadar bekle
            wait.until(ExpectedConditions.elementToBeClickable(womanButton));

            try {
                womanButton.click();
                logger.info("Women selected with normal click");
            } catch (Exception e) {
                // Normal click olmazsa JS click fallback
                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", womanButton);
                logger.info("Women selected with JS click fallback");
            }

            // Popup kapanana kadar bekle (isteğe bağlı)
            wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("genderWomanButton")));

        } catch (TimeoutException e) {
            logger.info("Gender popup not found or already closed");
        } catch (Exception e) {
            logger.error("Error selecting gender: " + e.getMessage());
        }


        }


}