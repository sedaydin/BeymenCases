package com.beymen.Page;

import com.beymen.Base.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;
import java.util.Random;

public class SearchPage extends BasePage {

    @FindBy(css = ".o-productList__item, .m-productCard, [class*='row o-productList__container']")
    private List<WebElement> productList;

    public SearchPage(WebDriver driver) {
        super(driver);
    }


    public ProductPage selectRandomProduct() {
        try {
            Thread.sleep(5000);
            wait.until(driver -> productList.size() > 1);

            Random random = new Random();
            int randomIndex = random.nextInt(Math.min(productList.size(), 3));

            WebElement selectedProduct = productList.get(randomIndex);
            logger.info("Selected product index: " + randomIndex);

            click(selectedProduct);

            return new ProductPage(driver);
        } catch (Exception e) {
            logger.error("Error selecting random product", e);
            throw new RuntimeException("Could not select product", e);
        }
    }
}