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
            result = "Акционная цена на главной странице и на странице товара не совпадают";
        }
        System.out.println(result);
    }

    @Test
    @Order(3)
    //в) обычная цена зачёркнутая и серая (можно считать, что "серый" цвет это такой, у которого в RGBa представлении одинаковые значения для каналов R, G и B)
    public void regularPriceStrikethroughAndGray() {
        driver.get("http://localhost/litecart/en/");
        WebElement product = driver.findElement(By.xpath("//div[@id='box-campaigns']//ul[@class='listing-wrapper products']/li[@class='product column shadow hover-light']/a[@class='link']"));
        WebElement regular_price_color = driver.findElement(By.xpath("//div[@id='box-campaigns']//s[@class='regular-price']"));
        String check_regular_price_color = regular_price_color.getCssValue("color");
        String[] rgba;
        rgba = check_regular_price_color.replace("rgba(", "").replace(")", "").split(", ");
        int r = Integer.parseInt(rgba[0]);
        int g = Integer.parseInt(rgba[1]);
        int b = Integer.parseInt(rgba[2]);
        int a = Integer.parseInt(rgba[3]);
        String check_regular_price_style = regular_price_color.getCssValue("text-decoration-style");//зачеркнутость обычной цены

        product.click();

        WebElement regular_price_color_productpage = driver.findElement(By.xpath("//div[@class='information']//s[@class='regular-price']"));
        String check_regular_price_color_productpage = regular_price_color_productpage.getCssValue("color");//цвет обычной цены в окне продукта
        String[] rgba2 = check_regular_price_color_productpage.replace("rgba(", "").replace(")", "").split(", ");
        int r2 = Integer.parseInt(rgba2[0]);
        int g2 = Integer.parseInt(rgba2[1]);
        int b2 = Integer.parseInt(rgba2[2]);
        int a2 = Integer.parseInt(rgba2[3]);
        String check_regular_price_style_productpage = regular_price_color_productpage.getCssValue("text-decoration-style");//зачеркнутость обычной цены на странице продукта

        if (r == g) {
            if (g == b) {
                System.out.println("Цвет обычной цены на главной странице серый");
            }
        } else {
            System.out.println("Цвет обычной на главной странице цены не серый");
        }
        if (check_regular_price_style.equals("solid")) {
            System.out.println("Шрифт обычной на главной странице цены зачеркнутый");
        } else {
            System.out.println("Шрифт обычной на главной странице цены не зачеркнутый");
        }

        if (r2 == g2) {
            if (g2 == b2) {
                System.out.println("Цвет обычной цены на странице продукта серый");
            }
        } else {
            System.out.println("Цвет обычной цены на странице продукта не серый");
        }

        if (check_regular_price_style_productpage.equals("solid")) {
            System.out.println("Шрифт обычной цены на странице продукта зачеркнутый");
        } else {
            System.out.println("Шрифт обычной цены на странице продукта не зачеркнутый");
        }
}
    @Test
    @Order(4)
    /*г) акционная жирная и красная (можно считать, что "красный" цвет это такой, у которого в RGBa представлении каналы G и B имеют нулевые значения)
(цвета надо проверить на каждой странице независимо, при этом цвета на разных страницах могут не совпадать)*/
    public void promotionPriceBoldAndRed(){
        driver.get("http://localhost/litecart/en/");
        WebElement product = driver.findElement(By.xpath("//div[@id='box-campaigns']//ul[@class='listing-wrapper products']/li[@class='product column shadow hover-light']/a[@class='link']"));
        WebElement promotion_price_color = driver.findElement(By.xpath("//div[@id='box-campaigns']//strong[@class='campaign-price']"));
        String check_ppc = promotion_price_color.getCssValue("color");//цвет акционной цены
        String[] rgba1 = check_ppc.replace("rgba(", "").replace(")", "").split(", ");
        int g1 = Integer.parseInt(rgba1[1]);
        int b1 = Integer.parseInt(rgba1[2]);
        String check_weight_promotion_price = promotion_price_color.getCssValue("font-weight");//толщина акционной цены

        product.click();

        WebElement promotion_price_color_productpage = driver.findElement(By.xpath("//strong[@class='campaign-price']"));
        String check_promotion_price_color_productpage = promotion_price_color_productpage.getCssValue("color");//цвет акционной цена на странице продукта
        String[] rgba3 = check_promotion_price_color_productpage.replace("rgba(", "").replace(")", "").split(", ");
        int g2 = Integer.parseInt(rgba3[1]);
        int b2 = Integer.parseInt(rgba3[2]);
        String check_promotion_price_weight_productpage = promotion_price_color_productpage.getCssValue("font-weight");//толщина акционной цены на странице продукта

        if (g1 == 0) {
            if (b1 == 0) {
                System.out.println("Цвет акционной цены на главной странице красный");
            }
        } else {
            System.out.println("Цвет акционной цены на главной странице не красный");
        }
        if (Integer.parseInt(check_weight_promotion_price) >= 700) {
            System.out.println("Шрифт акционной цены на главной странице жирный");
        } else {
            System.out.println("Шрифт акционной цены на главной странице не жирный");
        }
        if (g2 == 0) {
            if (b2 == 0) {
                System.out.println("Цвет акционной цены на странице продукта красный");
            }
        } else {
            System.out.println("Цвет акционной цены на странице продукта не красный");
        }
        String result;
        if (Integer.parseInt(check_promotion_price_weight_productpage) >= 700) {
            result = "жирный";
        } else {
            result = "не жирный";
        }
        System.out.println("Шрифт акционной цены на странице продукта: " + result);

    }

    @Test
    @Order(5)
    //д) акционная цена крупнее, чем обычная (это тоже надо проверить на каждой странице независимо)
    public void promotionalPriceIsBiggerThanRegularPrice(){
        driver.get("http://localhost/litecart/en/");
        WebElement product = driver.findElement(By.xpath("//div[@id='box-campaigns']//ul[@class='listing-wrapper products']/li[@class='product column shadow hover-light']/a[@class='link']"));
        WebElement font_size_regular_price = driver.findElement(By.xpath("//div[@id='box-campaigns']//s[@class='regular-price']"));//обычная цена на главной странице
        String check_size_regular_price = font_size_regular_price.getCssValue("font-size");//размер обычной цены на главной странице
        String regular_font_size = check_size_regular_price.replaceAll("px", " ");
        double db_test_size_main_price = Double.valueOf(regular_font_size);
        WebElement font_size_sale_price = driver.findElement(By.xpath("//div[@id='box-campaigns']//strong[@class='campaign-price']"));//акционная цена на гл.стр.
        String check_size_sale_price = font_size_sale_price.getCssValue("font-size");//размер акционной цены на гл.стр.
        String promotion_font_size = check_size_sale_price.replaceAll("px", " ");
        double db_test_size_sale_price = Double.valueOf(promotion_font_size);

        product.click();

        //WebElement regular_price_on_product_page = driver.findElement(By.xpath("//div[@class='information']//s[@class='regular-price']"));//обычная цена в окне продукта
        String font_regular_product_size = check_size_sale_price.replaceAll("px", " ");//размер обычной цены на экране продукта
        double db_test_size_product_main_price = Double.valueOf(font_regular_product_size);
        WebElement size_product_sale_price = driver.findElement(By.xpath("//strong[@class='campaign-price']"));//акционная цена на экране продукта
        String check_size_product_sale_price = size_product_sale_price.getCssValue("font-size");//размер акционной цены на странице продукта
        String font_size_sale_product = check_size_product_sale_price.replaceAll("px", " ");
        double db_test_size_sale_product_price = Double.valueOf(font_size_sale_product);

        String result;
        if (db_test_size_sale_price > db_test_size_main_price) {
            result = "больше";
        } else {
            result = "меньше";
        }
        System.out.println("Акционная цена " + result + ", чем обычная цена на главной странице ");

        if (db_test_size_sale_product_price > db_test_size_product_main_price) {
            result = "больше";
        } else {
            result = "меньше";
        }
        System.out.println("Акционная цена " + result + ", чем обычная цена на странице продукта");
    }

    @AfterEach
    public void stop() {
        driver.quit();
        driver = null;
    }
}
