package service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import entity.DBInfo;
import util.MyDBUtil;

public class DBService {
  protected final static Logger logger = LogManager.getLogger();

  // -7 BIT -6 TINYINT -5 BIGINT -4 LONGVARBINARY -3 VARBINARY -2 BINARY
  // -1 LONGVARCHAR 0 NULL 1 CHAR 2 NUMERIC 3 DECIMAL 4 INTEGER 5 SMALLINT
  // 6 FLOAT 7 REAL 8 DOUBLE 12 VARCHAR 91 DATE 92 TIME 93 TIMESTAMP 1111 OTHER
  public List<List<String>> query(DBInfo dbInfo) throws Exception {
    List<List<String>> rowList = new ArrayList<List<String>>();

    StringBuilder uri = new StringBuilder();
    uri.append("jdbc:mysql://")
        .append(dbInfo.getHost())
        .append(":")
        .append(dbInfo.getPort())
        .append("/")
        .append(dbInfo.getDatabase())
        .append(
            "?zeroDateTimeBehavior=convertToNull&useUnicode=true&characterEncoding=utf8");

    String sql = dbInfo.getSql().trim();
    if (!sql.toLowerCase().contains("limit")
        && sql.toLowerCase().startsWith("select")) {
      if (sql.endsWith(";")) {
        sql = sql.substring(0, sql.length() - 1);
      }
      sql += " LIMIT 500;";
    }

    Connection conn = null;
    Statement stmt = null;
    ResultSet rs = null;
    try {
      Class.forName("com.mysql.jdbc.Driver").newInstance();
      conn = DriverManager.getConnection(uri.toString(), dbInfo.getUsername(),
          dbInfo.getPassword());
      stmt = conn.createStatement();
      stmt.setQueryTimeout(dbInfo.getQueryTimeoutSeconds());

      /* In case of "set time_zone='+00:00';set names 'utf8';sql" */
      String[] sqls = sql.split(";");
      if (sqls.length > 1) {
        for (int k = 0; k < sqls.length - 1; k++) {
          if (!"".endsWith(sqls[k].trim()))
            logger.info("Executing " + sqls[k]);
          stmt.executeQuery(sqls[k]);
        }
      }

      // rs = stmt.executeQuery(sql);
      logger.info("Executing " + sqls[sqls.length - 1]);
      rs = stmt.executeQuery(sqls[sqls.length - 1]);
      ResultSetMetaData md = rs.getMetaData();
      List<String> columnLabelList = new ArrayList<String>();
      for (int i = 1; i <= md.getColumnCount(); i++) {
        columnLabelList.add(md.getColumnLabel(i));
      }
      rowList.add(columnLabelList);

      String value = null;
      while (rs.next()) {
        List<String> columnValueList = new ArrayList<String>();
        for (int i = 1; i <= md.getColumnCount(); i++) {
          // If DB "0000-00-00 00:00:00", Java return null
          // If DB "1111-11-11 11:11:11", Java return "1111-11-11 11:11:11.0"
          if (md.getColumnType(i) == 91 || md.getColumnType(i) == 93) {
            if (rs.getString(md.getColumnLabel(i)) == null) {
              value = "0000-00-00 00:00:00";
            } else {
              value = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(rs
                  .getTimestamp(md.getColumnLabel(i)));
            }
          } else {
            value = rs.getString(md.getColumnLabel(i));
          }
          columnValueList.add(value);
        }
        rowList.add(columnValueList);
      }
    } finally {
      MyDBUtil.close(rs, stmt, conn);
    }
    return rowList;
  }

}