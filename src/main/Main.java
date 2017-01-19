package main;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import entity.FTPInfo;
import service.CreateTableSQL2ExcelService;
import service.FTPService;
import service.SQL2ExcelService;
import util.ConfigUtil;

public class Main {
  protected final static Logger logger = LogManager.getLogger();

  public static void main(String[] args) {
    logger.info("----------NEW ROUND----------");
    
    CreateTableSQL2ExcelService sql2ExcelService = new CreateTableSQL2ExcelService();
    long beginTime = System.nanoTime();
    String fileName = sql2ExcelService.generateExcel();
    long endTime = System.nanoTime();
    String elapsedTime = String.format("%.3f",
        (endTime - beginTime) / 1000000000.0);
    
    if (fileName != null) {
      logger.info("Success generate excel file " + fileName
          + ", Total elapsed time is " + elapsedTime + "s");
      
//      FTPInfo ftpInfo = ConfigUtil.getFtpInfo();
//      if (ftpInfo != null) {
//        FTPService ftpService = new FTPService();
//        if (ftpService.upload(ftpInfo, fileName)) {
//          logger.info("Success upload file");
//        }
//      }
    } else {
      logger.error("Error generate excel file");
    }
  }
}