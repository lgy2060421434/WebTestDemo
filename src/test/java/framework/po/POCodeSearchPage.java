package framework.po;

import org.openqa.selenium.By;

public class POCodeSearchPage extends POBasePage{
    public String get_first_title(){
        String TagName=driver.findElement(By.id("search-term")).getTagName();
        System.out.println("这是标签名字："+TagName);
        return TagName;
    }
}
