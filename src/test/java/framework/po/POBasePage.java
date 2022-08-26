package framework.po;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

public class POBasePage {
    //note:yaml里的名字：MainPage，或者SearchPage
    public String name;
    //    methods 存储一整个yaml文件数据
    public HashMap<String, List<HashMap<String, Object>>> methods;
    WebDriver driver;
    Integer retryTimes = 3;

    //note:从po的yaml文件读取数据，并生成一个BasePage的实例
    public static POBasePage load(String name, WebDriver driver) {
        //note：每次调用都要清空一下对象的数据
        POBasePage page = null;
        String path = String.format("src/test/java/framework/po/%s.yaml",name);
//        note：如果文件存在，则执行loadFromFile，不存在则执行loadFromClassLoader
        if (new File(path).exists()) {
            page = loadFromFile(path);
        } else {
            page = loadFromClassLoader(name);
        }
        page.driver = driver;
        return page;
    }

    public static POBasePage loadFromFile(String path) {
//        yaml文件序列化，然后拿到文件数据
        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
        try {
            return mapper.readValue(new File(path), POBasePage.class);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    //note：反射执行测试类
    public static POBasePage loadFromClassLoader(String classname) {
        try {
            return (POBasePage) Class.forName(classname).getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public void runPOMethod(String methodName) {
        if (methods == null) {
            try {
                this.getClass().getMethod(methodName).invoke(this, null);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            }
            return;
        }
        AtomicReference<By> by = new AtomicReference<>();
        methods.get(methodName).forEach(step -> {
            step.entrySet().forEach(entry -> {
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
                    }else if (Locator_by.equals("link_text")){
                        by.set(By.partialLinkText(Locator_value));
                    }
                } else if (action.equals("click")) {
                    click(by.get());
                } else if (action.equals("sendeks")) {
                    sendKeys(by.get(), (String) value);
                } else if (action.equals("chrome")) {
                    driver = new ChromeDriver();
                } else if (action.equals("ipm_wait")) {
                    driver.manage().timeouts().implicitlyWait((Integer) value, TimeUnit.SECONDS);
                }
            });
        });
    }

//    public POBasePage(WebDriver driver) {
//        this.driver = driver;
//    }

    public boolean click(By by) {
        //note: 突然的弹框阻挡。异常处理，流程调整
        //note: find找不到，弹框阻挡，加载延迟
        //note: click的时候报错
        //note: click的时候不生效
        //note: click的时候被弹框阻挡
        try {
            driver.findElement(by).click();
        } catch (Exception e) {
            e.printStackTrace();
            retryTimes += 1;
            if (retryTimes < 4) {
                //todo:解决弹窗
                this.handleAlert();
                return false;
            }
        }
        return true;
    }

    public void sendKeys(By by, String content) {
        driver.findElement(by).sendKeys(content);
    }

    public void clickUntil(By by, By next) {
        //todo:用来解决前几次点击不生效，后面点击生效的过程，使用显示等待
    }

    public void handleAlert() {

    }
}
