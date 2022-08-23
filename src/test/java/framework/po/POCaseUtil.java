package framework.po;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

public class POCaseUtil {
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

    public static void run(POTestCase potestCase) {
        AtomicReference<POBasePage> lastPage = new AtomicReference<>();
        AtomicReference<By> by = new AtomicReference<>();
        //note：拿到PoSearchTest.yaml文件所有的值取出steps，遍历
        potestCase.getSteps().forEach(steps -> {
//          note:循环遍历得到PoSearchTest.yaml中的steps键值
            steps.entrySet().forEach(entry -> {
                String action = entry.getKey().toLowerCase();
                Object value = entry.getValue();
                //note:拿到action进行.的分割,得到键的两部分
                String[] actionArray = action.split("\\.");
                if (actionArray.length > 1) {
                    //note：键的第一部分
                    String poName = actionArray[0];
                    //note：键的第二部分
                    String poMethod = actionArray[1];
                    System.out.println(String.format("%s.%s  ( %s )", poName, poMethod, value));
                    if (poMethod.equals("new")) {
                        POBasePage currentPage = null;
                        //note:如果是第一次初始化POBasePage对象就获取第一个yaml的值
                        if (lastPage.get() == null) {
                            currentPage = (POBasePage.load(String.format("src/test/java/framework/po/%s.yaml", value), null));
                            //note:已经被初始化过就走这一步
                        } else {
                            currentPage = POBasePage.load(String.format("src/test/java/framework/po/%s.yaml", value), lastPage.get().driver);
                        }
                        //note：放入一个POBasePage对象加载了yaml文件的数据的
                        lastPage.set(currentPage);
                        //note：把对象加载到的数据放到POStore里的map对象里
                        POStore.getInstance().setPO(poName, currentPage);
                        //note:如果不是new，就拿POStore对象map集合的值
                    } else {
                        //note: POBasePage==POStore.getInstance().getPO(poName)  poName=main_page    POStore.getInstance()=new POStore
                        //note：如果不是加载数据，不是new，就执行测试用例驱动浏览器
                        POStore.getInstance().getPO(poName).runPOMethod(poMethod);
                    }
                }
            });
        });
    }
}
