package com.meyoung.actions;

import com.meyoung.driver.SeleniumDriver;
import com.meyoung.findElement.WebElementUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;

public class Actions extends SeleniumDriver {
    //点击方法
    public static void click(By by){
        WebElementUtils.findElement(by).click();
    }

    //文本框输入
    public static void sendText(By by,String text){
        WebElement element = WebElementUtils.findElement(by);
        element.clear();
        element.sendKeys(text);
    }

    //打开网站
    public static void openUrl(String url){
        if(!url.startsWith("http")){
            url = "http://"+url;
        }
        driver.get(url);
    }

    //获取文本
    public static String getText(By by){
        String text = WebElementUtils.findElement(by).getText();
        return text;
    }
    //获取多个文本
    public static ArrayList<String> getTexts(By by){
        ArrayList<String> arrayList = new ArrayList<String>();
        List<WebElement> elements = WebElementUtils.findElements(by);
        for (int i = 0; i <elements.size() ; i++) {
            String text = elements.get(i).getText();
            arrayList.add(text);
        }
        return arrayList;
    }

    //双击
    public static void  doubleClick(By by){
        WebElement element = WebElementUtils.findElement(by);
        org.openqa.selenium.interactions.Actions actions = new org.openqa.selenium.interactions.Actions(driver);
        actions.doubleClick(element).perform();
    }

}
