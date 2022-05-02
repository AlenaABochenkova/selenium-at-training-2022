import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import java.util.List;
import java.util.Set;

import static java.lang.Thread.sleep;

public class LinksOpenInANewWindow {

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
    public void linksOpenInANewWindow(){
        //1) зайти в админку
        //2) открыть пункт меню Countries (или страницу http://localhost/litecart/admin/?app=countries&doc=countries)
        driver.navigate().to("http://localhost/litecart/admin/?app=countries&doc=countries");
        driver.findElement(By.name("username")).sendKeys("admin");
        driver.findElement(By.name("password")).sendKeys("admin");
        driver.findElement(By.name("login")).click();
        //3) открыть на редактирование какую-нибудь страну или начать создание новой
        driver.findElement(By.linkText("China")).click();
        String mainWindow = driver.getWindowHandle();

        /*4) возле некоторых полей есть ссылки с иконкой в виде квадратика со стрелкой -- они ведут на внешние страницы
        и открываются в новом окне, именно это и нужно проверить.*/
        List<WebElement> link_buttons = driver.findElements(By.xpath("//i[@class='fa fa-external-link']"));
        int size_of_links = link_buttons.size();
        for (int i=0; i<size_of_links; i++) {
            Set<String> existingWindows = driver.getWindowHandles();
            link_buttons.get(i).click();
            safeSleep(1000);
            String newWindow = wait.until(anyWindowOtherThan(existingWindows));
            driver.switchTo().window(newWindow);
            driver.close();
            driver.switchTo().window(mainWindow);
        }
        driver.quit();
    }

    public static ExpectedCondition<String> anyWindowOtherThan(Set<String> oldWindows) {
        return new ExpectedCondition<String>() {
            public String apply(WebDriver driver) {
                Set<String>handles = driver.getWindowHandles();
                handles.removeAll(oldWindows);
                return handles.size()>0?handles.iterator().next():null;

            }
        };
    }
}
