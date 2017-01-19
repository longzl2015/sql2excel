package service;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import util.ConfigUtil;
import entity.DBInfo;

// Poor Obfuscation Implementation (POI)
// Horrible SpreadSheet Format (HSSF)
// Horrible Word Processor Format (HWPF)
// Horrible Property Set Format (HPSF)
// https://poi.apache.org/spreadsheet/examples.html
public class SQL2ExcelService {
  protected final static Logger logger = LogManager.getLogger();

//  public String generateExcel(List<DBInfo> dbList) {
//    logger.info("Start generate excel file");
//    String fileName = null;
//
//    // Workbook -> Sheet -> Row -> Cell
//    @SuppressWarnings("resource")
//    Workbook workbook = new HSSFWorkbook();
//    Map<String, Sheet> sheetMap = new HashMap<String, Sheet>();
//
//    {
//      logger.info("Current task is " + dbInfo.toString());
//      long beginTime = System.nanoTime();
//
//      Sheet sheet = null;
//      if (sheetMap.containsKey(dbInfo.getSheetName())) {
//        sheet = sheetMap.get(dbInfo.getSheetName());
//      } else {
//        sheet = workbook.createSheet(dbInfo.getSheetName());
//        sheetMap.put(dbInfo.getSheetName(), sheet);
//      }
//
//      DBService dbService = new DBService();
//      try {
//        List<List<String>> rowList = dbService.query(dbInfo);
//        /* sheet.getPhysicalNumberOfRows() = sheet.getLastRowNum()+1 */
//        int physicalNumberOfRows = sheet.getPhysicalNumberOfRows();
//        /* Add an empty line separator if append to an existing sheet */
//        if (physicalNumberOfRows > 0) {
//          sheet.createRow(physicalNumberOfRows);
//          physicalNumberOfRows++;
//        }
//        /* Add title */
//        if (dbInfo.getSqlTitle() != null && !"".equals(dbInfo.getSqlTitle())) {
//          Row row = sheet.createRow(physicalNumberOfRows);
//          row.createCell(0).setCellValue(dbInfo.getSqlTitle());
//          /* [IMPORTANT]It will degrade the performance */
//          // sheet.autoSizeColumn(0);
//          physicalNumberOfRows++;
//        }
//        /* Padding data */
//        for (int i = 0; i < rowList.size(); i++) {
//          Row row = sheet.createRow(physicalNumberOfRows + i);
//          List<String> cellList = rowList.get(i);
//          for (int j = 0; j < cellList.size(); j++) {
//            row.createCell(j).setCellValue(cellList.get(j));
//            // sheet.autoSizeColumn(j);
//          }
//        }
//      } catch (Exception e) {
//        e.printStackTrace();
//      }
//
//      long endTime = System.nanoTime();
//      String elapsedTime = String.format("%.3f",
//          (endTime - beginTime) / 1000000000.0);
//      logger.info("Current task elapsed time is " + elapsedTime + "s");
//    }
//
//    String currentTime = new SimpleDateFormat("yyyyMMddHHmmss").format(System
//        .currentTimeMillis());
//    fileName = ConfigUtil.getWorkbookName() + "_" + currentTime + ".xls";
//
//    OutputStream outputStream = null;
//    try {
//      outputStream = new FileOutputStream("./data/" + fileName);
//      workbook.write(outputStream);
//    } catch (Exception e) {
//      fileName = null;
//      e.printStackTrace();
//    } finally {
//      try {
//        outputStream.close();
//      } catch (IOException e) {
//        e.printStackTrace();
//      }
//    }
//
//    return fileName;
//  }
}