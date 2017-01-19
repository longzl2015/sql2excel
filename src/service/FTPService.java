package service;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import entity.FTPInfo;

// Default value
// ftpClient.getConnectTimeout()  0
// ftpClient.getControlKeepAliveTimeout() 0
// ftpClient.getControlKeepAliveReplyTimeout() 1000
// ftpClient.getDefaultTimeout()  0
// ftpClient.getSoTimeout() 10000
// http://commons.apache.org/proper/commons-net/apidocs/org/apache/commons/net/ftp/FTPClient.html
public class FTPService {
  protected final static Logger logger = LogManager.getLogger();

  public boolean upload(FTPInfo ftpInfo, String fileName) {
    logger.info("Start upload file");
    boolean uploadStatus = false;

    FTPClient ftpClient = new FTPClient();

    ftpClient.setControlEncoding("UTF-8"); // FileName
    ftpClient.setConnectTimeout(30000); // Connect timed out, connect()
    ftpClient.setDefaultTimeout(60000); // Read timed out, login()

    InputStream inputStream = null;
    try {
      ftpClient.connect(ftpInfo.getHost(), ftpInfo.getPort());
      logger.info(ftpClient.getReplyString().trim());
      ftpClient.login(ftpInfo.getUsername(), ftpInfo.getPassword());
      logger.info(ftpClient.getReplyString().trim());

      // Default, it causes a PORT command
      // ftpClient.enterLocalActiveMode();
      // causes a PASV command
      ftpClient.enterLocalPassiveMode();
      ftpClient.setFileType(FTP.BINARY_FILE_TYPE); // FTP.ASCII_FILE_TYPE
      inputStream = new FileInputStream("./data/" + fileName);
      uploadStatus = ftpClient.storeFile(fileName, inputStream);
      logger.info(ftpClient.getReplyString().trim());

      ftpClient.logout();
    } catch (Exception e) {
      logger.error("Error when upload file");
      e.printStackTrace();
    } finally {
      if (ftpClient.isConnected()) {
        try {
          ftpClient.disconnect();
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
      try {
        if (inputStream != null)
          inputStream.close();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }

    return uploadStatus;
  }
}