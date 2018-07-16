package com.meyoung.testTools;

import com.meyoung.tools.ExcelTool;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class TestExcelTool {

    public  Object[][] getTestData(String filePath) {
        Workbook wb = ExcelTool.getWorkBook(filePath);
        //获得sheet
        Sheet sheet = wb.getSheetAt(0);
        //获取总行数
        int rowTotleNum = sheet.getLastRowNum() + 1;
        //获取总列数
        int columns = sheet.getRow(0).getPhysicalNumberOfCells();

        //获得首行所有列名
        ArrayList<String> keyArray = new ArrayList<String>();
        for (int i = 0; i < columns; i++) {
            String cellValue = ExcelTool.getCellValue(sheet, 0, i);
            keyArray.add(cellValue);
        }

        //把数据放到二维数组hashmap中,因为第一行是标题，所以数据行数是总行-1
        HashMap<String, String>[][] map = new HashMap[rowTotleNum - 1][1];
        //如果只有一行，那就是只有标题没有数据
        if(rowTotleNum>1){
            //初始化数组中的hashmap
            for (int i = 0; i < rowTotleNum-1 ; i++) {
                map[i][0] = new HashMap<String, String>();
            }
        }else {
            //logger.error(filePath+"   excel中没有数据，请检查");
        }

        //遍历单元格，并把值添加到hashmap中（key value）
        //第一行是标题，前面已经取出来了
        for (int r = 1; r < rowTotleNum; r++) {
            //双层循环，这一层循环一行的所有列
            for (int c = 0; c < columns ; c++) {
                String cellValue = ExcelTool.getCellValue(sheet, r, c);
                //数组索引是从0开始的，从keyArray根据列索引中取到对应的列标题，
                map[r-1][0].put(keyArray.get(c),cellValue);
            }
        }

        return map;
    }

    @DataProvider(name="t")
    public  Object[][] data1(){
        TestExcelTool testExcelTool = new TestExcelTool();
        return testExcelTool.getTestData(".\\a.xls");
    }

    @Test(dataProvider = "t")
    public void test1(HashMap<String,String> data){
        System.out.println(data.get("username"));
        System.out.println(data.get("pwd"));
    }

}
