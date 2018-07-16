package com.meyoung.testFingElement;

import com.meyoung.driver.SeleniumDriver;
import com.meyoung.findElement.WebElementUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.Test;

public class TestWebelementUtils {
    public void TestFindElement(){
        WebDriver driver = SeleniumDriver.openChrome();
        driver.get("file:///C:/selenium_html/index.html");
        WebElementUtils.findElement(By.className("wait")).click();
        String text = WebElementUtils.findElement(By.className("red")).getText();
        System.out.println(text);

    }
    @Test
    public void test1(){
        System.out.println(6666);
    }
}
