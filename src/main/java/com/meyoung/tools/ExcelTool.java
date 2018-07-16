package com.meyoung.tools;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;

public class ExcelTool {
    public static Workbook getWorkBook(String filePath) {
        Workbook wb = null;
        try {
            if(filePath.endsWith(".xls")){
                File file = new File(filePath);
                InputStream is = new FileInputStream(file);
                wb = new HSSFWorkbook(is);

            }else if(filePath.endsWith(".xlsx")){
                wb = new XSSFWorkbook(filePath);
            }
        }catch (IOException e){
            e.printStackTrace();
        }finally {
            return wb;
        }
    }

    public static String getCellValue(Sheet sheet,int rowNum,int cellNum){
        Cell cell = sheet.getRow(rowNum).getCell(cellNum);
        String value = ExcelTool.getCellValue(cell);
        return value;
    }

    public static  String getCellValue(Cell cell){
        String value ="";
        switch (cell.getCellTypeEnum()){
            case STRING:
                value = String.valueOf(cell.getRichStringCellValue());
                return value;
            case NUMERIC:
                value = String.valueOf(cell.getNumericCellValue());
                return value;
            case BOOLEAN:
                value = String.valueOf(cell.getBooleanCellValue());
                return value;
            case FORMULA:
                value = String.valueOf(cell.getCellFormula());
                return value;
            case ERROR:
                value = String.valueOf(cell.getErrorCellValue());
            case BLANK:
                return value;
            default:
                return value;
        }
    }

    //提取数据到dataProvider
    public static Object[][] getTestData(String filePath) {
        Workbook wb = getWorkBook(filePath);
        //获得sheet
        Sheet sheet = wb.getSheetAt(0);
        //获取总行数
        int rowTotleNum = sheet.getLastRowNum() + 1;
        //获取总列数
        int columns = sheet.getRow(0).getPhysicalNumberOfCells();

        //获得首行所有列名
        ArrayList<String> keyArray = new ArrayList<String>();
        for (int i = 0; i < columns; i++) {
            String cellValue = getCellValue(sheet, 0, i);
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
                String cellValue =getCellValue(sheet, r, c);
                //数组索引是从0开始的，从keyArray根据列索引中取到对应的列标题，
                map[r-1][0].put(keyArray.get(c),cellValue);
            }
        }

        return map;
    }

    //创建excel
    public static  void createExcel(String filePath,String sheetName,String titleRow[]){
        //判断文件格式
        if(filePath.endsWith(".xls")){
            //创建workbook
            HSSFWorkbook workbook = new HSSFWorkbook();
            //添加worksheet，不添加sheet，生成的xls文件打开会报错
            HSSFSheet sheet = workbook.createSheet(sheetName);
            //创建文件
            FileOutputStream fileOutputStream = null;
            try {
                //添加表头
                Row row = workbook.getSheet(sheetName).createRow(0);
                for (int i = 0; i < titleRow.length; i++) {
                    Cell cell = row.createCell(i);
                    cell.setCellValue(titleRow[i]);
                }
                fileOutputStream = new FileOutputStream(filePath);
                workbook.write(fileOutputStream);
            }catch (Exception e){
                e.printStackTrace();
            }finally {
                try {
                    fileOutputStream.close();
                }catch (IOException e){
                    e.printStackTrace();
                }
            }
        }else{
            //log只能创建xls的excel
        }
    }
}
