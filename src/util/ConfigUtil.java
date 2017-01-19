package util;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import main.Main;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import entity.DBInfo;
import entity.FTPInfo;

public class ConfigUtil {
    protected final static Logger logger = LogManager.getLogger();

    private static String workbookName = null;
    private static List<DBInfo> dbList = null;
    private static FTPInfo ftpInfo = null;
    private static String sqlPath = null;
    private static String sqlCommentPath = null;

    static {
        InputStream inStream = Main.class.getClassLoader().getResourceAsStream(
                "sql2excel.properties");
        Properties properties = new Properties();
        try {
            properties.load(inStream);
            sqlPath = properties.getProperty("sql_path");
            workbookName = properties.getProperty("workbook_name");
            sqlCommentPath = properties.getProperty("sqlComment_path");
            dbList = new ArrayList<DBInfo>();
            String[] sqlList = properties.getProperty("sql_list").split(",");
            for (String sql : sqlList) {
                dbList.add(new DBInfo(properties.getProperty(sql + "_db_host"), Integer
                        .parseInt(properties.getProperty(sql + "_db_port")), properties
                        .getProperty(sql + "_db_username"), properties.getProperty(sql
                        + "_db_password"), properties.getProperty(sql + "_db_database"),
                        properties.getProperty(sql + "_db_sql"), Integer
                        .parseInt(properties.getProperty(sql
                                + "_db_query_timeout_seconds")), properties.getProperty(sql
                        + "_sheet_name"), properties.getProperty(sql + "_sql_title")));
            }

            if (Integer.parseInt(properties.getProperty("is_upload_to_ftp")) == 1) {
                ftpInfo = new FTPInfo();
                ftpInfo.setHost(properties.getProperty("ftp_host"));
                ftpInfo.setPort(Integer.parseInt(properties.getProperty("ftp_port")));
                ftpInfo.setUsername(properties.getProperty("ftp_username"));
                ftpInfo.setPassword(properties.getProperty("ftp_password"));
                ftpInfo.setUploadTimeoutSeconds(Integer.parseInt(properties
                        .getProperty("ftp_upload_timeout_seconds")));
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                inStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        logger.info("ConfigUtil initialize OK");
    }


    public static String getWorkbookName() {
        return workbookName;
    }

    public static List<DBInfo> getDbList() {
        return dbList;
    }

    public static FTPInfo getFtpInfo() {
        return ftpInfo;
    }


    public static String getSqlPath() {
        return sqlPath;
    }

    public static String getSqlCommentPath() {
        return sqlCommentPath;
    }
}