package com.meyoung.driver;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;

public class SeleniumDriver {
    public static WebDriver driver;
    static String path = System.getProperty("user.dir");
    public static WebDriver openBrowser (String browser){
        String path = System.getProperty("user.dir");
        if (browser.equals("chrome")){
            System.setProperty("webdriver.chrome.driver",path+"/drivers/chromedriver.exe");
            driver = new ChromeDriver();
        }else if (browser.equals("firefox")){
            driver = new FirefoxDriver();
        }else if (browser.equals("ie")){
            System.setProperty("webdriver.ie.driver",path+"/drivers/IEDriverServer.exe");
            driver = new InternetExplorerDriver();
        }else if (browser.equals("edge")){
            System.setProperty("webdriver.edge.driver",path+"/drivers/MicrosoftWebDriver.exe");
            driver = new EdgeDriver();
        }else{
            System.out.println("未识别"+browser);
        }

        return driver;
    }

    public static WebDriver openChrome (){
        System.setProperty("webdriver.chrome.driver",path+"/drivers/chromedriver.exe");
        return driver =  new ChromeDriver();
    }

    public static WebDriver openFirefox (){
        return driver = new FirefoxDriver();
    }

    public static WebDriver openIE (){
        System.setProperty("webdriver.ie.driver",path+"/drivers/IEDriverServer.exe");
        return driver = new InternetExplorerDriver();
    }
    public static WebDriver openEdge (){
        System.setProperty("webdriver.edge.driver",path+"/drivers/MicrosoftWebDriver.exe");
        return driver = new EdgeDriver();
    }

    public static void closeBrowser(){
        driver.close();
    }
    public static void quitBrowser(){
        driver.quit();
    }
}
