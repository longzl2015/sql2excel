package entity;

public class FTPInfo {
  private String host;
  private int port;
  private String username;
  private String password;
  private int uploadTimeoutSeconds;

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

  public int getUploadTimeoutSeconds() {
    return uploadTimeoutSeconds;
  }

  public void setUploadTimeoutSeconds(int uploadTimeoutSeconds) {
    this.uploadTimeoutSeconds = uploadTimeoutSeconds;
  }

  @Override
  public String toString() {
    return "FTPInfo [host=" + host + ", port=" + port + ", username="
        + username + ", password=" + password + ", uploadTimeoutSeconds="
        + uploadTimeoutSeconds + "]";
  }
}
