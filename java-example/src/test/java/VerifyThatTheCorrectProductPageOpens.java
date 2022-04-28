import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class VerifyThatTheCorrectProductPageOpens {
    private static WebDriver driver;
    private WebDriverWait wait;

    @BeforeEach
    public void start() {
        System.setProperty("webdriver.chrome.driver", "C:\\Tools\\chromedriver.exe");
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    @Test
    @Order(1)
    //а) на главной странице и на странице товара совпадает текст названия товара
    public void theProductNameOnTheMainPageAndTheProductPageAreTheSame() {
        driver.get("http://localhost/litecart/en/");
        WebElement product = driver.findElement(By.xpath("//div[@id='box-campaigns']//ul[@class='listing-wrapper products']/li[@class='product column shadow hover-light']/a[@class='link']"));
        WebElement name_a = driver.findElement(By.xpath("//div[@id='box-campaigns']//div[@class='name']"));//имя на главной странице
        String check_name_a = name_a.getAttribute("textContent");
        product.click();
        WebElement name_b = driver.findElement(By.xpath("//h1[@class='title']"));//имя на странице товара
        String check_name_b = name_b.getAttribute("textContent");
        String result;
        if (check_name_a.equals(check_name_b)) {
            result = "Текст названия товара на главной странице и на странице товара совпадает";
        } else {
            result = "Текст названия товара на главной странице и на странице товара не совпадает";
        }
        System.out.println(result);
    }

    @Test
    @Order(2)
    //б) на главной странице и на странице товара совпадают цены (обычная и акционная)
    public void pricesAreTheSameOnTheHomepageAndOnTheProductPage() {
        driver.get("http://localhost/litecart/en/");
        WebElement product = driver.findElement(By.xpath("//div[@id='box-campaigns']//ul[@class='listing-wrapper products']/li[@class='product column shadow hover-light']/a[@class='link']"));
        WebElement regular_price = driver.findElement(By.xpath("//div[@id='box-campaigns']//s[@class='regular-price']"));//обычная цена на главной странице
        String price_a = regular_price.getAttribute("textContent");
        WebElement sale_price = driver.findElement(By.xpath("//div[@id='box-campaigns']//strong[@class='campaign-price']"));//акционная цена на главной странице
        String price_b = sale_price.getAttribute("textContent");
        product.click();
        WebElement regular_price_in = driver.findElement(By.xpath("//div[@class='information']//s[@class='regular-price']"));//обычная цена на странице товара
        String price_c = regular_price_in.getAttribute("textContent");
        WebElement sale_price_in = driver.findElement(By.xpath("//strong[@class='campaign-price']"));//акционная цена на странице товара
        String price_d = sale_price_in.getAttribute("textContent");
        String result;
        if (price_a.equals(price_c)) {
            result = "Обычная цена на главной странице и на странице товара совпадают";
        } else {
            result = "Обычная цена на главной странице и на странице товара не совпадают";
        }
        System.out.println(result);

        if (price_b.equals(price_d)) {
            result = "Акционная цена на главной странице и на странице товара совпадают";
        } else {
            result = "Акционная цена на главной странице и на странице товара совпадают";
        }
        System.out.println(result);
    }

    @Test
    @Order(3)
    //в) обычная цена зачёркнутая и серая (можно считать, что "серый" цвет это такой, у которого в RGBa представлении одинаковые значения для каналов R, G и B)
    public void regularPriceStrikethroughAndGray() {
        driver.get("http://localhost/litecart/en/");
        WebElement regular_price_color = driver.findElement(By.xpath("//div[@id='box-campaigns']//s[@class='regular-price']"));
        String test_color_main_price = regular_price_color.getCssValue("color");
        String[] rgba;
        rgba = test_color_main_price.replace("rgba(", "").replace(")", "").split(", ");
        int r = Integer.parseInt(rgba[0]);
        int g = Integer.parseInt(rgba[1]);
        int b = Integer.parseInt(rgba[2]);
        int a = Integer.parseInt(rgba[3]);
        String check_text_style = regular_price_color.getCssValue("text-decoration-style");//зачеркнутость обычной цены
        if (r == g) {
            if (g == b) {
                System.out.println("Цвет обычной цены серый");
            }
        } else {
            System.out.println("Цвет обычной цены не серый");
        }
        if (check_text_style.equals("solid")) {
            System.out.println("Шрифт обычной цены зачеркнутый");
        } else {
            System.out.println("Шрифт обычной цены не зачеркнутый");
        }
}
    @Test
    @Order(4)
    /*г) акционная жирная и красная (можно считать, что "красный" цвет это такой, у которого в RGBa представлении каналы G и B имеют нулевые значения)
(цвета надо проверить на каждой странице независимо, при этом цвета на разных страницах могут не совпадать)*/
    public void promotionPriceBoldAndRed(){
        driver.get("http://localhost/litecart/en/");
        WebElement promotion_price_color = driver.findElement(By.xpath("//div[@id='box-campaigns']//strong[@class='campaign-price']"));
        String check_ppc = promotion_price_color.getCssValue("color");//цвет акционной цены
        String[] rgba1 = check_ppc.replace("rgba(", "").replace(")", "").split(", ");
        int g1 = Integer.parseInt(rgba1[1]);
        int b1 = Integer.parseInt(rgba1[2]);
        String check_weight_promotion_price = promotion_price_color.getCssValue("font-weight");//толщина акционной цены
        if (g1 == 0) {
            if (b1 == 0) {
                System.out.println("Цвет акционной цены красный");
            }
        } else {
            System.out.println("Цвет акционной цены не красный");
        }
        if (Integer.parseInt(check_weight_promotion_price) >= 700) {
            System.out.println("Шрифт акционной цены жирный");
        } else {
            System.out.println("Шрифт акционной цены не жирный");
        }
    }

    //@Test
    //@Order(5)
    //д) акционная цена крупнее, чем обычная (это тоже надо проверить на каждой странице независимо)


    @AfterEach
    public void stop() {
        driver.quit();
        driver = null;
    }
}
