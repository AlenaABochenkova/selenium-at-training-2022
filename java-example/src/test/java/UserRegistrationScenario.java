import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import java.util.Date;

public class UserRegistrationScenario {
    private static WebDriver driver;
    private WebDriverWait wait;

    @BeforeEach
    public void start() {
        System.setProperty("webdriver.chrome.driver", "C:\\Tools\\chromedriver.exe");
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    @Test
    public void userRegistrationScenario() {
        /*1) регистрация новой учётной записи с достаточно уникальным адресом электронной почты
        (чтобы не конфликтовало с ранее созданными пользователями, в том числе при предыдущих запусках того же самого сценария)*/
        driver.navigate().to("http://localhost/litecart/en/");
        driver.findElement(By.linkText("New customers click here")).click();//перейти в профиль регистрации нового пользователя
        driver.findElement(By.xpath("//input[@name='tax_id']")).sendKeys("1");
        driver.findElement(By.xpath("//input[@name='company']")).sendKeys("New_test_customers");
        driver.findElement(By.xpath("//input[@name='firstname']")).sendKeys("Alla");
        driver.findElement(By.xpath("//input[@name='lastname']")).sendKeys("Borisovna");
        driver.findElement(By.xpath("//input[@name='address1']")).sendKeys("Rose squere");
        driver.findElement(By.xpath("//input[@name='address2']")).sendKeys("First Floor");
        driver.findElement(By.xpath("//input[@name='postcode']")).sendKeys("55555");
        driver.findElement(By.xpath("//input[@name='city']")).sendKeys("Los Angeles");
        driver.findElement(By.xpath("//span[@class='select2-selection__arrow']")).click();
        driver.findElement(By.xpath("//input[@class='select2-search__field']")).sendKeys("United States"+ Keys.ENTER);//выбор страны

        Select zone = new Select(driver.findElement(By.xpath("//select[@name='zone_code']")));//выбор зоны
        JavascriptExecutor javascriptExecutor = (JavascriptExecutor) driver;
        javascriptExecutor.executeScript("arguments[0].selectedIndex=11", zone);
        javascriptExecutor.executeScript("arguments[0].selectedIndex=11;arguments[0].dispatchEvent(new Event('change'));", zone);

        String email = new Date().getTime() + "@example.ru";
        driver.findElement(By.xpath("//input[@name='email']")).sendKeys(email);
        driver.findElement(By.xpath("//input[@name='phone']")).sendKeys("+123456789");

        String password = "1234";
        driver.findElement(By.xpath("//input[@name='password']")).sendKeys(password);
        driver.findElement(By.xpath("//input[@name='confirmed_password']")).sendKeys(password);
        driver.findElement(By.xpath("//button[@name='create_account']")).click();
        //2) выход (logout), потому что после успешной регистрации автоматически происходит вход
        driver.findElement(By.xpath("//div[@id='box-account']/div[@class='content']/ul/li[last()]/a")).click();

        //3) повторный вход в только что созданную учётную запись
        driver.findElement(By.xpath("//input[@name='email']")).sendKeys(email);
        driver.findElement(By.xpath("//input[@name='password']")).sendKeys(password);
        driver.findElement(By.xpath("//button[@name='login']")).click();
        //4) и ещё раз выход.
        driver.findElement(By.xpath("//div[@id='box-account']/div[@class='content']/ul/li[last()]/a")).click();
    }

    @AfterEach
    public void stop() {
        driver.quit();
        driver = null;
    }
}

