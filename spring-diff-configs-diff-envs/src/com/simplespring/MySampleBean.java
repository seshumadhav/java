package com.simplespring;

public class MySampleBean {

  private String propToBeInsertedFromSpringXml;

  private String id;
  private String name;
  private String env;
  private String url;
  private String username;
  private String password;

  public String getEnv() {
    return env;
  }

  public void setEnv(String env) {
    this.env = env;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
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

  public void setPropToBeInsertedFromSpringXml(String propToBeInsertedFromXml) {
    this.propToBeInsertedFromSpringXml = propToBeInsertedFromXml;
  }

  public String getPropToBeInsertedFromSpringXml() {
    return propToBeInsertedFromSpringXml;
  }

  @Override
  public String toString() {
    return "MySampleBean [propToBeInsertedFromSpringXml="
        + propToBeInsertedFromSpringXml + ", id=" + id + ", name=" + name
        + ", env=" + env + ", url=" + url + ", username=" + username
        + ", password=" + password + "]";
  }

}
