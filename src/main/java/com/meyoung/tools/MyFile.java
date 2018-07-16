package com.meyoung.tools;

import java.io.File;

public class MyFile {

    //判断文件是否存在
    public boolean fileExists(String filePath) {
        return new File(filePath).exists();
    }

    //判断文件是否存在，没有就新建
    public void creatFile(String filePath) {
        MyFile myFile = new MyFile();
        if (!myFile.fileExists(filePath)){
            File file = new File(filePath);
            file.mkdirs();
        }
    }

}
