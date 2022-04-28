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

public class SortingCountriesAndGeofencesOnTheCountriesPage {
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
        driver.navigate().to("http://localhost/litecart/admin/?app=countries&doc=countries");
        driver.findElement(By.name("username")).sendKeys("admin");
        driver.findElement(By.name("password")).sendKeys("admin");
        driver.findElement(By.name("login")).click();
        /*Writing countries and zones to variables*/
        List<WebElement> countries = driver.findElements(By.xpath("//table[@class='dataTable']//tr[@class='row']//td[5]"));
        List<WebElement> zones = driver.findElements(By.xpath("//table[@class='dataTable']//tr[@class='row']//td[6]"));
        int zoneslist = zones.size();
        /*Default arraylist set of countries*/
        List<String> all_countries = new ArrayList<>();
        for (int a = 0; a < countries.size(); a++) {
            all_countries.add(countries.get(a).getText());
        }
        /*Array with sorted countries*/
        List<String> sorted_countries = new ArrayList<>(all_countries);
        Collections.sort(sorted_countries);
        /*Checking that countries are in alphabetical order*/
        for (int b = 0; b < all_countries.size(); b++) {
            if (all_countries.get(b).equals(sorted_countries.get(b))) {
            } else {
                System.out.println("Страны расположены не по алфавиту!");
            }
        }
        /*Countries which have more zones than zero*/
        for (int i = 0; i < zoneslist; i++) {
            WebElement zone = zones.get(i);
            String check = zone.getAttribute("textContent");
            if (check.equals("0")) {
            }
            /*Opens the country page and checks that the geozones ordered by alphabetical*/
            else {
                zone.findElement(By.xpath("./preceding-sibling::td[1]/a")).click();
                List<WebElement> zone2 = driver.findElements(By.xpath("//table[@id='table-zones']//td[3]"));
                int size2 = zone2.size() - 1;
                List<String> all_elements = new ArrayList<>();
                for (int j = 0; j < size2; j++) {

                    all_elements.add(zone2.get(j).getText());
                }
                List<String> all_elements_sort = new ArrayList<>(all_elements);
                Collections.sort(all_elements_sort);
                for (int y = 0; y < size2; y++) {
                    if (all_elements.get(y).equals(all_elements_sort.get(y))) {
                    } else {
                        System.out.println("Страны расположены не по алфавиту!");
                        break;
                    }
                }
                driver.navigate().back();
                zones = driver.findElements(By.xpath("//table[@class='dataTable']//tr[@class='row']//td[6]"));
            }
        }
    }
            @AfterEach
            public void stop() {
                driver.quit();
                driver = null;
            }
    }
