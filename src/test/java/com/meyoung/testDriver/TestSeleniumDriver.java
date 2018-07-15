package com.meyoung.testDriver;

import com.meyoung.driver.SeleniumDriver;
import org.junit.Test;
import org.openqa.selenium.WebDriver;

public class TestSeleniumDriver {
    @Test
    public void testOpenBrowser() throws InterruptedException {
        //WebDriver driver = SeleniumDriver.openFirefox();
        SeleniumDriver.openFirefox();
        Thread.sleep(2000);
        SeleniumDriver.quitBrowser();
    }
}
