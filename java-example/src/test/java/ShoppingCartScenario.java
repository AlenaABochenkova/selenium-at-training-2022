import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import java.util.List;

public class ShoppingCartScenario {

    private WebDriver driver;
    private WebDriverWait wait;

    @BeforeEach
    public void start() {
        System.setProperty("webdriver.chrome.driver", "C:\\Tools\\chromedriver.exe");
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    }

    @Test
    public void shoppingCartScenario() {

        //1) открыть главную страницу
        driver.navigate().to("http://localhost/litecart/en/");
        JavascriptExecutor jse = (JavascriptExecutor) driver;
        jse.executeScript("window.scrollBy(0,500)");
        //2) открыть первый товар из списка
        //2) добавить его в корзину (при этом может случайно добавиться товар, который там уже есть, ничего страшного)
        List<WebElement> products = driver.findElements(By.xpath("//div[@id='box-most-popular']//li/a[1]"));
        int size = products.size();
        for (int i = 0; i < 3; i++) {
            products.get(i).click();
            try {
                new Select(driver.findElement(By.name("options[Size]"))).selectByValue("Small");
            } catch (Exception ex) {
            }
            driver.findElement(By.name("add_cart_product")).click();
            WebElement quantity = driver.findElement(By.xpath("//div[@id='cart']//a[@class='content']/span[@class='quantity']"));
            Integer expectedQuantity = Integer.parseInt(quantity.getText()) + 1;
            //3) подождать, пока счётчик товаров в корзине обновится
            wait.until(ExpectedConditions.textToBePresentInElement(quantity, expectedQuantity.toString()));
            //4) вернуться на главную страницу, повторить предыдущие шаги ещё два раза, чтобы в общей сложности в корзине было 3 единицы товара
            driver.navigate().back();
            products = driver.findElements(By.xpath("//div[@id='box-most-popular']//li/a[1]"));
        }

        //5) открыть корзину (в правом верхнем углу кликнуть по ссылке Checkout)
        driver.findElement(By.linkText("Checkout »")).click();
        //6) удалить все товары из корзины один за другим, после каждого удаления подождать, пока внизу обновится таблица
        while (driver.findElements(By.xpath("//button[@name='remove_cart_item']")).size() != 0) {
            WebElement remove_button = driver.findElement(By.xpath("//button[@name='remove_cart_item']"));
            remove_button.click();
            wait.until(ExpectedConditions.stalenessOf(remove_button));
        }
    }

    @AfterEach
    public void stop() {
        driver.quit();
        driver = null;
    }
}
