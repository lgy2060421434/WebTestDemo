package framework;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.concurrent.TimeUnit;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.MatcherAssert.assertThat;

public class SearchTest {
    static WebDriver driver;
    @BeforeAll
    public static void beforAll(){
        driver=new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    }
    @AfterAll
    public static void afterAll(){
        driver.quit();
    }

    @Test
    public void search(){
        driver.get("https://ceshiren.com");
        driver.findElement(By.id("search-button")).click();
        driver.findElement(By.id("search-term")).sendKeys("定向班5期");
        driver.findElements(By.xpath("//span[@class='search-item-slug']")).get(0).click();
        String teite=driver.findElement(By.id("search-term")).getTagName();
        assertThat(teite,containsString("input"));
    }
}
