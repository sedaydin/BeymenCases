import com.beymen.Page.BasketPage;
import com.beymen.Page.HomePage;
import com.beymen.Page.ProductPage;
import com.beymen.Page.SearchPage;
import com.beymen.Util.DriverUtil;
import com.beymen.Util.ExcelUtil;
import com.beymen.Util.FileUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.*;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

public class BeymenTest  {
    public final Logger logger = LogManager.getLogger(BeymenTest.class);
    public static WebDriver driver;
    public HomePage homePage;
    public final String EXCEL_PATH = "src/main/resources/testdata/search_keywords.xlsx";
    public final String BASE_URL = "https://www.beymen.com";

    @BeforeClass
    public static void setupClass() {
        driver = DriverUtil.getDriver();
        driver.manage().window().maximize();

    }

    @Before
    public void setup() {
        driver.get(BASE_URL);
        homePage = new HomePage(driver);

        ChromeOptions options = new ChromeOptions();

        // 🔒 Gizli sekme (Incognito)
        options.addArguments("--incognito");

        // 🔕 Bildirimleri engelle
        Map<String, Object> prefs = new HashMap<>();
        prefs.put("profile.default_content_setting_values.notifications", 2); // 1 = izin ver, 2 = engelle
        options.setExperimentalOption("prefs", prefs);

    }

    @Test
    public void testBeymenShoppingFlow() {
        logger.info("Test case execution started");

        // Ana sayfanın  kontrolü
        assertTrue("Home page should be displayed", homePage.isHomePageDisplayed());

        logger.info("Step 1: Home page verified");
        homePage.handleCookiePopup();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        homePage.gender();



        // Excel'den "şort" kelimesini oku (Sütun 0, Satır 0)
        String firstKeyword = ExcelUtil.getCellData(EXCEL_PATH, 0, 0);

        // Excel'den "gömlek" kelimesini oku (Sütun 1, Satır 0)
        String secondKeyword = ExcelUtil.getCellData(EXCEL_PATH, 0,1 );


        homePage.searchProduct(firstKeyword);
        logger.info("Step 2: Searched for: " + firstKeyword);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

        // Arama kutusunu temizle
        homePage.clearSearchBox();
        logger.info("Step 3: Search box cleared");
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        

        homePage.searchProduct2(secondKeyword);
        logger.info("Step 4: Searched for: " + secondKeyword);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

        // Enter tuşuna bas
        SearchPage searchResultPage = homePage.pressEnter();
        logger.info("Step 5: Pressed Enter");

        // Rastgele ürün seç

        ProductPage productDetailPage = searchResultPage.selectRandomProduct();
        logger.info("Step 6: Random product selected");

        // Ürün bilgilerini al
        String productName = productDetailPage.getProductName();
        String productPrice = productDetailPage.getProductPrice();
        logger.info("Step 7: Product info retrieved");

        productDetailPage.selectSize();
        logger.info("Size Selected");

        // Ürün bilgilerini dosyaya yaz
        FileUtil.writeProductInfo(productName, productPrice);
        logger.info("Step 8: Product info written to file");

        // Ürünü sepete ekle
        BasketPage basketPage = productDetailPage.addBasket();
        basketPage.goBasket();
        logger.info("Step 9: Product added to cart");


        // Fiyat karşılaştırması yap
        String cartPrice = basketPage.getBasketPrice();
        String cleanProductPrice = productPrice.replaceAll("[^0-9,.]", "");
        String cleanCartPrice = cartPrice.replaceAll("[^0-9,.]", "");

        logger.info("Comparing prices - Product: " + cleanProductPrice + ", Basket: " + cleanCartPrice);
        assertTrue("Prices should match",
                cleanProductPrice.contains(cleanCartPrice) || cleanCartPrice.contains(cleanProductPrice));
        logger.info("Step 10: Price verification successful");

        // Adet arttır
        basketPage.increaseQuantity();
        String quantity = basketPage.getQuantity();
        assertEquals("Quantity should be 2", "2", quantity);
        logger.info("Step 11: Quantity increased to 2");


        // Ürünü sepetten sil
        basketPage.removeProduct();
        assertTrue("Basket should be empty", basketPage.isCartEmpty());
        logger.info("Step 12: Product removed, basket is empty");

        logger.info("Test case completed successfully");
    }

    @AfterClass
    public static void tearDownClass() {
        DriverUtil.quitDriver();

    }
}