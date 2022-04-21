import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class ProductHasStickers {

    private static WebDriver driver;
    private WebDriverWait wait;

    @BeforeEach
    public void start() {
        System.setProperty("webdriver.chrome.driver", "C:\\Tools\\chromedriver.exe");
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        }

    @Test
    public void productHasStickers() {
        /*Admin login*/
        driver.navigate().to("http://localhost/litecart/en/");
        /*Find products with stickers*/
        List<WebElement> amount = driver.findElements(By.xpath("//ul[@class='listing-wrapper products']/li"));
        int a = amount.size();
        System.out.println("Товаров на странице " + a);
        for (WebElement  i : amount) {
            List<WebElement> check = i.findElements(By.xpath(".//div[contains(@class,\"sticker\")]"));
            System.out.println("Товар содержит " + check.size() + " стикер");
            }
        }

    @AfterEach
    public void stop () {
        driver.quit();
        driver = null;
    }
}