package automatiotest;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ChromeTest {
    private WebDriver driver;

    @Before
    public void setUp() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("https://rozetka.com.ua/");
    }

    @After
    public void tearDown() { driver.close(); }

    @Test
    public void inCart() {
        //Додавання товару в кошик та перевірка, що товар в кошику
        WebDriverWait wait = new WebDriverWait(driver, 10);
        boolean present;

        driver.findElement(By.xpath("//input[@name='text']")).sendKeys("durex");
        driver.findElement(By.xpath("//button[text()='Найти']")).click();
        driver.findElement(By.xpath("//*[@id=\"image_item30086847\"]")).click();
        String nameOfItem = driver.findElement(By.xpath("//h1[@class='detail-title']")).getText();
        driver.findElement(By.xpath("//*[@name=\"topurchases\"]")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"cart-popup\"]/a")));
        driver.findElement(By.xpath("//*[@id=\"cart-popup\"]/a")).click();
        driver.findElement(By.xpath("//div[contains(@id, 'cart_block')]")).click();
//      перевірка наявності товару в кошику
        try {
            driver.findElement(By.linkText(nameOfItem));
            present = true;
        } catch (NoSuchElementException e) {
            present = false;
        }

        boolean notInCart = false;
        boolean inCart = true;
        Assert.assertEquals(present, inCart);
//      це повинно було бути повідомлення у разі фейлу тесту, але
//      сприймає його як перевірку вірності тесту. на занятті запитаю)
//      Assert.assertEquals("Item isn't in the cart", notInCart, present);
    }

    @Test
    public void homeTest() {
        driver.findElement(By.xpath("//li[@id='9017']")).click();

        //Перевірка переходу на потрібну сторінку
        String expectedUrl = "https://rozetka.com.ua/sport-i-uvlecheniya/c4627893/";
        String actualUrl = driver.getCurrentUrl();

        Assert.assertEquals("URLs aren't equal", expectedUrl, actualUrl);
        Assert.assertEquals(expectedUrl, actualUrl);
    }
}
