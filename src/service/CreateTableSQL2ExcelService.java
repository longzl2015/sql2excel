package service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import util.ConfigUtil;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.List;

// Poor Obfuscation Implementation (POI)
// Horrible SpreadSheet Format (HSSF)
// Horrible Word Processor Format (HWPF)
// Horrible Property Set Format (HPSF)
// https://poi.apache.org/spreadsheet/examples.html
public class CreateTableSQL2ExcelService {
    private final static Logger logger = LogManager.getLogger();

    public String generateExcel() {
        logger.info("Start generate excel file");
        String fileName;

        // Workbook -> Sheet -> Row -> Cell
        @SuppressWarnings("resource")
        Workbook workbook = new HSSFWorkbook();
        {
            long beginTime = System.nanoTime();
            Sheet sheet = workbook.createSheet();
            SQLParseService service = new SQLParseService();
            try {
                List<List<String>> rowList = service.parse();
                int physicalNumberOfRows = sheet.getPhysicalNumberOfRows();
                if (physicalNumberOfRows > 0) {
                    sheet.createRow(physicalNumberOfRows);
                    physicalNumberOfRows++;
                }

        /* Padding data */
                for (int i = 0; i < rowList.size(); i++) {
                    Row row = sheet.createRow(physicalNumberOfRows + i);
                    List<String> cellList = rowList.get(i);
                    for (int j = 0; j < cellList.size(); j++) {
                        row.createCell(j).setCellValue(cellList.get(j));
                        sheet.autoSizeColumn(j);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            long endTime = System.nanoTime();
            String elapsedTime = String.format("%.3f",
                    (endTime - beginTime) / 1000000000.0);
            logger.info("Current task elapsed time is " + elapsedTime + "s");
        }

        String currentTime = new SimpleDateFormat("yyyyMMddHHmmss").format(System
                .currentTimeMillis());
        fileName = ConfigUtil.getWorkbookName() + "_" + currentTime + ".xls";

        OutputStream outputStream = null;
        try {
            outputStream = new FileOutputStream(fileName);
            workbook.write(outputStream);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (outputStream != null) {
                    outputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return fileName;
    }
}