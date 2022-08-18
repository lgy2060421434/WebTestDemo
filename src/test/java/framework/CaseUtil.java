package framework;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

public class CaseUtil {
    static WebDriver driver;

    @BeforeAll
    public static void beforAll() {
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    }

    @AfterAll
    public static void afterAll() {
        driver.quit();
    }
    public static void run(TestCase testCase){
        System.out.println(testCase.getName());
        System.out.println(testCase.getSteps());
        AtomicReference<By> by = new AtomicReference<>();
        testCase.getSteps().forEach(steps -> {
            steps.entrySet().forEach(entry -> {
                String action = entry.getKey().toLowerCase();
                Object value = entry.getValue();
                if (action.equals("get")) {
                    driver.get((String) value);
                } else if (action.equals("find")) {
                    ArrayList<String> values = (ArrayList<String>) value;
                    String Locator_by = values.get(0);
                    String Locator_value = values.get(1);
                    if (Locator_by.equals("id")) {
                        by.set(By.id(Locator_value));
                    } else if (Locator_by.equals("css")) {
                        by.set(By.cssSelector(Locator_value));
                    } else if (Locator_by.equals("xpath")) {
                        by.set(By.cssSelector(Locator_value));
                    }
                }else if (action.equals("click")){
                    driver.findElement(by.get()).click();
                }else if (action.equals("sendkeys")){
                    driver.findElement(by.get()).sendKeys((String)value);
                }else if (action.equals("chrome")){
                    driver=new ChromeDriver();
                }else if (action.equals("ipm_wait")){
                    driver.manage().timeouts().implicitlyWait((Integer)value, TimeUnit.SECONDS);
                }
            });
        });
    }
}
