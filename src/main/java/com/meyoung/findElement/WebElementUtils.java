package com.meyoung.findElement;

import com.meyoung.driver.SeleniumDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.ArrayList;
import java.util.List;

public class WebElementUtils extends SeleniumDriver {
    public static WebElement findElement(final By by){
        WebElement element = null;
        try {
            WebDriverWait wait = new WebDriverWait(driver, 30);
            wait.until(ExpectedConditions.presenceOfElementLocated(by));
            element = driver.findElement(by);
        }catch (Exception e){
            System.out.println("查找元素 "+by+" 超时");
            e.printStackTrace();
        }

        return element;
    }

    public static List<WebElement> findElements(final By by){
        List<WebElement> elements = null;
        try {
            WebDriverWait wait = new WebDriverWait(driver, 30);
            wait.until(ExpectedConditions.presenceOfElementLocated(by));
           elements = driver.findElements(by);

        }catch (Exception e){
            System.out.println("查找元素 "+by+" 超时");
            e.printStackTrace();
        }

        return elements;
    }
}
