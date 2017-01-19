package entity;

public class DBInfo {
  private String host;
  private int port;
  private String username;
  private String password;
  private String database;
  private String sql;
  private int queryTimeoutSeconds;
  private String sheetName;
  private String sqlTitle;

  public DBInfo(String host, int port, String username, String password,
      String database, String sql, int queryTimeoutSeconds, String sheetName,
      String sqlTitle) {
    this.host = host;
    this.port = port;
    this.username = username;
    this.password = password;
    this.database = database;
    this.sql = sql;
    this.queryTimeoutSeconds = queryTimeoutSeconds;
    this.sheetName = sheetName;
    this.sqlTitle = sqlTitle;
  }

  public String getHost() {
    return host;
  }

  public void setHost(String host) {
    this.host = host;
  }

  public int getPort() {
    return port;
  }

  public void setPort(int port) {
    this.port = port;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getDatabase() {
    return database;
  }

  public void setDatabase(String database) {
    this.database = database;
  }

  public String getSql() {
    return sql;
  }

  public void setSql(String sql) {
    this.sql = sql;
  }

  public int getQueryTimeoutSeconds() {
    return queryTimeoutSeconds;
  }

  public void setQueryTimeoutSeconds(int queryTimeoutSeconds) {
    this.queryTimeoutSeconds = queryTimeoutSeconds;
  }

  public String getSheetName() {
    return sheetName;
  }

  public void setSheetName(String sheetName) {
    this.sheetName = sheetName;
  }

  public String getSqlTitle() {
    return sqlTitle;
  }

  public void setSqlTitle(String sqlTitle) {
    this.sqlTitle = sqlTitle;
  }

  @Override
  public String toString() {
    return "DBInfo [host=" + host + ", port=" + port + ", username=" + username
        + ", database=" + database + ", sql=" + sql + ", queryTimeoutSeconds="
        + queryTimeoutSeconds + ", sheetName=" + sheetName + ", sqlTitle="
        + sqlTitle + "]";
  }
}