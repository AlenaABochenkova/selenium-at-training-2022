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
import java.util.Collections;
import java.util.List;

public class CheckGeozonesSortingOnGeozonesPage {
    private static WebDriver driver;
    private WebDriverWait wait;

    @BeforeEach
    public void start() {
        System.setProperty("webdriver.chrome.driver", "C:\\Tools\\chromedriver.exe");
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }
    @Test
    public void sortingCountriesAndGeofencesOnTheCountriesPage() {
        /*Admin login*/
        driver.navigate().to("http://localhost/litecart/admin/?app=geo_zones&doc=geo_zones");
        driver.findElement(By.name("username")).sendKeys("admin");
        driver.findElement(By.name("password")).sendKeys("admin");
        driver.findElement(By.name("login")).click();
        /*Writing countries to variable*/
        List<WebElement> countries = driver.findElements(By.xpath("//table[@class='dataTable']//tr[@class='row']//td[3]"));
        int countrieslist = countries.size();
        /*Check the countrieslist has zones which ordered by alphabetical*/
        for (int i=0; i < countrieslist; i++) {
            countries = driver.findElements(By.xpath("//table[@class='dataTable']//tr[@class='row']//td[3]/a"));
            countries.get(i).click();
            List<WebElement> zone = driver.findElements(By.xpath("//table[@id='table-zones']//td[3]//select[contains(@name,'zones[')]//option[@selected]"));
            int a = zone.size();
            List<String> all_countries = new ArrayList<>();
            for (int j=0; j < a; j++) {
               
                all_countries.add(zone.get(j).getText());
            }
            List<String> sorted_countries = new ArrayList<>(all_countries);
            Collections.sort(sorted_countries);
            for (int k=0; k < a; k++) {
                if (all_countries.get(k).equals(sorted_countries.get(k))) {
                }
                else {
                    System.out.println("Страны расположены не по алфавиту!");
                    break;
                }
            }
            driver.navigate().back();
        }
    }
    @AfterEach
    public void stop() {
        driver.quit();
        driver = null;
    }
}
