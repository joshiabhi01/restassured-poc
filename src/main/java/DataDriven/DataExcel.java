/**
 * Problem Statement:
 * Author: Abhishek Joshi
 * User:joshia3
 * Date:23-07-2025
 * Time:12:23
 */
package DataDriven;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.NumberToTextConverter;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

public class DataExcel {

    public ArrayList<String> getTestData(String sheetName,String columnName, String testCaseId) throws IOException {

        ArrayList<String> testData = new ArrayList<>();
        FileInputStream fileInputStream = new FileInputStream("C:\\Users\\joshia3\\OneDrive - Autodesk\\Documents\\POC\\BE\\Backend\\src\\main\\resources\\ExcelData\\event_data.xlsx");
        XSSFWorkbook workbook = new XSSFWorkbook(fileInputStream);

        int sheets = workbook.getNumberOfSheets();
        for (int i=0; i<sheets; i++){
            if(workbook.getSheetName(i).equalsIgnoreCase(sheetName)){
                XSSFSheet sheet = workbook.getSheetAt(i);

                Iterator<Row> rows = sheet.iterator();
                Row firstRow = rows.next();

                Iterator<Cell> cell = firstRow.cellIterator();
                int cellCount = 0,columnNumber = 0;
                while (cell.hasNext()){
                    Cell cellValue = cell.next();
                    if(cellValue.getStringCellValue().equalsIgnoreCase(columnName)){
                        columnNumber = cellCount;
                    }
                    cellCount++;
                }
                //System.out.println(columnNumber);

                while (rows.hasNext()){
                    Row row = rows.next();
                    if(row.getCell(columnNumber).getStringCellValue().equalsIgnoreCase(testCaseId)){
                        Iterator<Cell> eventData = row.cellIterator();
                        while(eventData.hasNext()){
                            Cell nextCell = eventData.next();

                            String cellType = nextCell.getCellType().toString();
                            if(cellType.equalsIgnoreCase("string")){
                                //System.out.println(nextCell.getStringCellValue());
                                testData.add(nextCell.getStringCellValue());
                            } else if (cellType.equalsIgnoreCase("numeric")) {

                                if(DateUtil.isCellDateFormatted(nextCell)){
                                    Date date = nextCell.getDateCellValue();
                                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
                                    //System.out.println(simpleDateFormat.format(date));
                                    testData.add(simpleDateFormat.format(date));
                                }else {
                                    //System.out.println(nextCell.getNumericCellValue());
                                    testData.add(NumberToTextConverter.toText(nextCell.getNumericCellValue()));
                                }
                            }
                        }
                    }
                }
            }
        }
        return testData;
    }


    public static void main(String[] args) throws IOException {
        DataExcel dataExcel = new DataExcel();
        System.out.println(dataExcel.getTestData("pipe_events","EventID","pXBlcu8xdpCs2RSgw3hql1bKCgskMw"));
        System.out.println(dataExcel.getTestData("pipe_events","EventID","nGjsz5ma7hXHMXHwjSFpuHy0y9eapK"));
        System.out.println(dataExcel.getTestData("pipe_events","EventID","sBzCDVnkFnFvFsp4SoBMnfsUW38Wt6"));
        System.out.println(dataExcel.getTestData("pipe_events","EventID","1vks2Av7fYetCYahkPi9yap2KzSwLT"));
        System.out.println(dataExcel.getTestData("pipe_events","EventID","a7uY3Fq7JMgTXaTvMkHgIaEk6bVazc"));
        System.out.println(dataExcel.getTestData("pipe_events","EventID","Crpt6rwsXlB0Po6SZEpZXlqsdLLFPC"));
    }
}
