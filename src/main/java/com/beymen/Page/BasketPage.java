package com.beymen.Page;


import com.beymen.Base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;

public class BasketPage extends BasePage {



    @FindBy(css = "div.priceBox__priceWrapper > span.priceBox__salePrice")
    private WebElement basketPrice;
    @FindBy(css = "a.o-header__userInfo--item.bwi-cart-o")
    private WebElement basketLink;


    @FindBy(css = "#quantitySelect0-key-0")
    private WebElement quantityInput;

    @FindBy(css = "[class*='quantity-increase'], [class*='quantityIncrease']")
    private WebElement increaseQuantityButton;

    @FindBy(css = "[class*='removeProduct'], [class*='delete'], button[title*='Sil']")
    private WebElement removeProductButton;

    @FindBy(css = "[class*='emptyCart'], .m-empty")
    private WebElement emptyBasketMessage;

    public BasketPage(WebDriver driver) {
        super(driver);
    }

    public void goBasket() {
        try {

            basketLink.click();
            logger.info("Navigated to basket");
            Thread.sleep(2000);
        } catch (Exception e) {
            logger.error("Error navigating to basket", e);
        }
    }

    public String getBasketPrice() {
        String price = getText(basketPrice);
        logger.info("Basket price: " + price);
        return price;
    }

    public void increaseQuantity() {
        try {
            click(increaseQuantityButton);
            logger.info("Increased quantity");
            Thread.sleep(1000);
        } catch (Exception e) {
            logger.error("Error increasing quantity", e);
        }
    }

    public String getQuantity() {
        WebElement quantityDropdown = driver.findElement(By.id("quantitySelect0-key-0"));
        // Select sınıfını kullanarak 2 adet'i seç
        Select select = new Select(quantityDropdown);
        select.selectByValue("2"); // value="2" olan <option>'u seçer

        String quantity = quantityInput.getAttribute("value");
        logger.info("Current quantity: " + quantity);

        // Select elementini bul

        return quantity;
    }

    public void removeProduct() {
        try {
            Thread.sleep(3000);
            WebElement removeproduct = driver.findElement(By.id("removeCartItemBtn0-key-0"));
            removeproduct.click();
            logger.info("Product removed from basket");
            Thread.sleep(2000);
        } catch (Exception e) {
            logger.error("Error removing product", e);
        }
    }

    public boolean isCartEmpty() {
        try {
            waitForElement(emptyBasketMessage);
            logger.info("Basket is empty");
            return emptyBasketMessage.isDisplayed();
        } catch (Exception e) {
            logger.info("Basket is not empty");
            return false;
        }
    }
}