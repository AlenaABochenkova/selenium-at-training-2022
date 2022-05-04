import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class NoMessagesInBrowserLog {

    private WebDriver driver;
    private WebDriverWait wait;

    @BeforeEach
    public void start() {
        System.setProperty("webdriver.chrome.driver", "C:\\Tools\\chromedriver.exe");
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    @Test
    public void noMessagesInBrowserLog(){
        //1) зайти в админку
        //2) открыть каталог, категорию, которая содержит товары (страница http://localhost/litecart/admin/?app=catalog&doc=catalog&category_id=1)
        driver.navigate().to("http://localhost/litecart/admin/?app=catalog&doc=catalog&category_id=1");
        driver.findElement(By.name("username")).sendKeys("admin");
        driver.findElement(By.name("password")).sendKeys("admin");
        driver.findElement(By.name("login")).click();

        //Определяем количество товаров в таблице
        List<WebElement> products_in_table = driver.findElements(By.xpath("//table[@class='dataTable']//td[3]/a"));
        int size = products_in_table.size();

        //Добавляем продукты в массив
        List <String> products = new ArrayList<String>();
        for (int i = 0; i < products_in_table.size(); i++) {
            products_in_table = driver.findElements(By.xpath("//table[@class='dataTable']//td[3]/a"));
            products.add(products_in_table.get(i).getText());
        }

        //3) последовательно открывать страницы товаров и проверять, не появляются ли в логе браузера сообщения (любого уровня)
        for (String product : products) {
            driver.findElement(By.linkText(product)).click();
            driver.manage().logs().get("browser").forEach(l -> System.out.println(l));
            driver.navigate().back();
        }
    }

    @AfterEach
    public void stop() {
        driver.quit();
        driver = null;
    }
}
