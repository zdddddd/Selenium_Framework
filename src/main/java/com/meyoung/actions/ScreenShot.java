package com.meyoung.actions;

import com.meyoung.driver.SeleniumDriver;
import com.meyoung.tools.DateFormat;
import com.meyoung.tools.MyFile;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.Augmenter;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ScreenShot extends SeleniumDriver {

    static String path = System.getProperties().getProperty("user.dir") + "/error/";
    public static void  screenShot(){
        WebDriver augmentDriver = new Augmenter().augment(driver);
        File file = ((TakesScreenshot) augmentDriver).getScreenshotAs(OutputType.FILE);
        try{
            MyFile myFile = new MyFile();
            //根据日期创建文件夹
            myFile.creatFile(path+DateFormat.format(DateFormat.DATE_FORMAT));
            FileUtils.copyFile(file,new File(path
                    + DateFormat.format(DateFormat.COMMON_DATE_FORMAT)
                    +"/"
                    + DateFormat.format(DateFormat.DATE_FORMAT)
                    +".jpg"));
        }catch (IOException e){
            //log 截图失败
            e.printStackTrace();
        }
    }

    public static void  screenShot(String fileName){
        WebDriver augmentDriver = new Augmenter().augment(driver);
        File file = ((TakesScreenshot) augmentDriver).getScreenshotAs(OutputType.FILE);
        try{
            MyFile myFile = new MyFile();
            //根据日期创建文件夹
            myFile.creatFile(path+DateFormat.format(DateFormat.DATE_FORMAT));
            FileUtils.copyFile(file,new File(path
                    + DateFormat.format(DateFormat.COMMON_DATE_FORMAT)
                    +"/"
                    + fileName
                    +".jpg"));
        }catch (IOException e){
            //log 截图失败
            e.printStackTrace();
        }
    }



    /*public void screenShot(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss");
        String screenName = String.valueOf(sdf.format(new Date().getTime()) + ".jpg");
        File dir = new File("test-output/snapshot");
        if (!dir.exists())
            dir.mkdirs();
        String screenShotFilePath = dir.getAbsolutePath() + "/" + screenName;
        this.screenShot(screenShotFilePath);
    }

    public void screenShot(String screenShotFilePath){
        try {
            File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            FileUtils.copyFile(scrFile, new File(screenShotFilePath));
        } catch (IOException e) {
            System.out.println("Screen shot error: " + screenShotFilePath);
        }
    }*/
}
