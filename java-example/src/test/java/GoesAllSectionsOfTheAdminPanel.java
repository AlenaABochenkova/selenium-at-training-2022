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
import static org.openqa.selenium.support.ui.ExpectedConditions.presenceOfElementLocated;

public class GoesAllSectionsOfTheAdminPanel {

    private WebDriver driver;
    private WebDriverWait wait;

    @BeforeEach
    public void start() {
        System.setProperty("webdriver.chrome.driver", "C:\\Tools\\chromedriver.exe");
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    @Test
    public void goesAllSectionsOgTheAdminPanel() {
        /*Admin login*/
        driver.navigate().to("http://localhost/litecart/admin");
        driver.findElement(By.name("username")).sendKeys("admin");
        driver.findElement(By.name("password")).sendKeys("admin");
        driver.findElement(By.name("login")).click();

        /*Goes through all sections of the admin panel*/
        List<WebElement> items = driver.findElements(By.xpath("//td[@id='sidebar']//div[@id='box-apps-menu-wrapper']//li[@id = 'app-']"));
        int size = items.size();
            for (int i=0; i<size; i++) {
                items = driver.findElements(By.xpath("//td[@id='sidebar']//div[@id='box-apps-menu-wrapper']//li[@id = 'app-']"));
                items.get(i).click();
                wait.until(presenceOfElementLocated(By.xpath("//td[@id='content']//h1")));

            boolean isElementPresent = driver.findElements(By.xpath("//td[@id='sidebar']//div[@id='box-apps-menu-wrapper']//li[@id = 'app-' and @class='selected']/ul[@class='docs']/li")).size() > 0;

            if (isElementPresent = true) {
                List<WebElement> items_selected = driver.findElements(By.xpath("//td[@id='sidebar']//div[@id='box-apps-menu-wrapper']//li[@id = 'app-' and @class='selected']/ul[@class='docs']/li"));
                int size1 = items_selected.size();
                    for (int j = 0; j<size1; j++){
                        items_selected = driver.findElements(By.xpath("//td[@id='sidebar']//div[@id='box-apps-menu-wrapper']//li[@id = 'app-' and @class='selected']/ul[@class='docs']/li"));
                        items_selected.get(j).click();
                        wait.until(presenceOfElementLocated(By.xpath("//td[@id='content']//h1")));

                }
            }
        }
    }

    @AfterEach
    public void stop() {
        driver.quit();
        driver = null;
    }
}