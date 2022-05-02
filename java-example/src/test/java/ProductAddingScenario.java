import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.io.File;
import java.time.Duration;
import static java.lang.Thread.sleep;

public class ProductAddingScenario {

    private WebDriver driver;
    private WebDriverWait wait;

    @BeforeEach
    public void start() {
        System.setProperty("webdriver.chrome.driver", "C:\\Tools\\chromedriver.exe");
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public void safeSleep(long time){
        try {
            sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void productAddingScenario(){
        driver.navigate().to("http://localhost/litecart/admin/?app=catalog&doc=catalog");
        driver.findElement(By.name("username")).sendKeys("admin");
        driver.findElement(By.name("password")).sendKeys("admin");
        driver.findElement(By.name("login")).click();

        //Добавление нового продукта на странице General
        driver.findElement(By.linkText("Add New Product")).click();
        driver.findElement(By.xpath("//input[@type='radio' and @value=1]")).click();
        driver.findElement(By.xpath("//input[@type='checkbox' and @value=1]")).click();
        driver.findElement(By.xpath("//input[@name = 'name[en]']")).sendKeys("Phoebe Buffay Duck");
        driver.findElement(By.xpath("//input[@name = 'code']")).sendKeys("Phoebe Buffay Duck");
        Select d_category = new Select(driver.findElement(By.xpath("//select[@name='default_category_id']")));
        d_category.selectByVisibleText("Rubber Ducks");
        driver.findElement(By.xpath("//input[@type='checkbox' and @value='1-1']")).click();
        driver.findElement(By.xpath("//input[@name = 'quantity']")).clear();
        driver.findElement(By.xpath("//input[@name = 'quantity']")).sendKeys("2");
        File upload = new File("fibi.jpg");
        String absolute = upload.getAbsolutePath();
        driver.findElement(By.xpath("//input[@name='new_images[]']")).sendKeys(absolute);
        driver.findElement(By.xpath("//input[@type='date' and @name='date_valid_from']")).clear();
        driver.findElement(By.xpath("//input[@type='date' and @name='date_valid_from']")).sendKeys("01.05.2022");
        driver.findElement(By.xpath("//input[@type='date' and @name='date_valid_to']")).clear();
        driver.findElement(By.xpath("//input[@type='date' and @name='date_valid_to']")).sendKeys("31.05.2022");

        //Добавление нового продукта на странице Information
        driver.findElement(By.linkText("Information")).click();
        safeSleep (1000);
        Select manufacturer = new Select(driver.findElement(By.xpath("//select[@name='manufacturer_id']")));
        manufacturer.selectByVisibleText("ACME Corp.");
        driver.findElement(By.xpath("//input[@name='keywords']")).sendKeys("Friends");
        driver.findElement(By.xpath("//input[@name='short_description[en]']")).sendKeys("Cutest");
        driver.findElement(By.xpath("//div[@class='trumbowyg-editor']")).sendKeys("Cutest");
        driver.findElement(By.xpath("//input[@name='head_title[en]']")).sendKeys("Phoebe Buffay Duck");
        driver.findElement(By.xpath("//input[@name='meta_description[en]']")).sendKeys("Phoebe Buffay Duck");

        //Добавление нового продукта на странице Prices
        driver.findElement(By.linkText("Prices")).click();
        safeSleep (1000);
        driver.findElement(By.xpath("//input[@name = 'purchase_price']")).clear();
        driver.findElement(By.xpath("//input[@name = 'purchase_price']")).sendKeys("25");
        Select currency = new Select(driver.findElement(By.xpath("//select[@name='purchase_price_currency_code']")));
        currency.selectByVisibleText("US Dollars");
        driver.findElement(By.xpath("//input[@name = 'prices[USD]']")).sendKeys("50");
        driver.findElement(By.xpath("//button[@name='save']")).click();

        //Проверка наличия продукта в админке
        driver.findElement(By.linkText("Phoebe Buffay Duck")).click();
        safeSleep (1000);
        //Проверка наличия продукта в магазине
        driver.navigate().to("http://localhost/litecart/en/");
        JavascriptExecutor jse = (JavascriptExecutor)driver;
        jse.executeScript("window.scrollBy(0,500)");
        safeSleep (1000);
    }

    @AfterEach
    public void stop() {
        driver.quit();
        driver = null;
    }
}
