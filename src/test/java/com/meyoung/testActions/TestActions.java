package com.meyoung.testActions;

import com.meyoung.actions.Actions;
import com.meyoung.driver.SeleniumDriver;
import org.junit.Test;
import org.openqa.selenium.By;

import java.util.ArrayList;

public class TestActions {

    @Test
    public void testClick() throws InterruptedException {
        SeleniumDriver.openChrome();
        Actions.openUrl("www.baidu.com");
        Actions.sendText(By.id("kw"),"selenium");
        Actions.click(By.id("su"));
        Thread.sleep(3000);
        SeleniumDriver.quitBrowser();
    }

    @Test
    public void  test111(){
        ArrayList<String> strings = new ArrayList<String>();
        strings.add("a");
        strings.add("b");
        strings.add("c");
        System.out.println(strings.size());
        System.out.println(strings.get(1));
        System.out.println(strings);
    }

    @Test
    public void  test222(){
        int [] a = {1,2,3};
        int [] b = new int[]{1,2,3};
        System.out.println(a.length);
        System.out.println(b.length);
    }

    @Test
    public void testGetText(){
        SeleniumDriver.openChrome();
        Actions.openUrl("http://www.baidu.com");
        String text = Actions.getText(By.name("tj_trnews"));
        System.out.println(text);
        SeleniumDriver.quitBrowser();
    }

    @Test
    public void testGetTexts(){
        SeleniumDriver.openChrome();
        Actions.openUrl("http:www.baidu.com");
        ArrayList<String> texts = Actions.getTexts(By.xpath("//*[@id='u1']/a"));
        System.out.print(texts);
        SeleniumDriver.quitBrowser();
    }

    @Test
    public void testDoubleClick(){
        SeleniumDriver.openChrome();
        Actions.openUrl("http:www.baidu.com");
        Actions.doubleClick(By.id("su"));
        SeleniumDriver.quitBrowser();
    }

}
