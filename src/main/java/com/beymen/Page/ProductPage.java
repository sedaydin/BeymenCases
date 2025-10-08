package com.beymen.Page;
import com.beymen.Base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class ProductPage extends BasePage {

    @FindBy(css = "h1[class*='title'], .o-productDetail__title, [class*='product-name']")
    private WebElement productTitle;

    @FindBy(css = "#priceNew")
    private WebElement productPrice;

    @FindBy(css = "[class*='add-to-basket'], [id*='addBasket'], button[title*='Sepete']")
    private WebElement addToCartButton;

    public ProductPage(WebDriver driver) {
        super(driver);
    }

    public String getProductName() {
        String name = getText(productTitle);
        logger.info("Product name: " + name);
        return name;
    }

    public String getProductPrice() {
        String price = getText(productPrice);
        logger.info("Product price: " + price);
        return price;
    }
    public void selectSize() {
        List<WebElement> sizes = driver.findElements(By.cssSelector("span.m-variation__item"));

        for (WebElement size : sizes) {
            if (size.isEnabled()) {
                size.click();
                break;
            }
        }


    }



    public BasketPage addBasket() {
        try {
            Thread.sleep(2000);
            click(addToCartButton);
            logger.info("Product added to basket");
            Thread.sleep(3000);
            return new BasketPage(driver);
        } catch (Exception e) {
            logger.error("Error adding product to basket", e);
            throw new RuntimeException("Could not add to basket", e);
        }
    }
}