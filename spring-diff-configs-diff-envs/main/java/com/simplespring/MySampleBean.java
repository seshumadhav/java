package com.simplespring;

public class MySampleBean {

  private String propToBeInsertedFromXml;

  public void setPropToBeInsertedFromXml(String propToBeInsertedFromXml) {
    this.propToBeInsertedFromXml = propToBeInsertedFromXml;
  }

  public void setPropToBeInsertedFromPropsFile(
      String propToBeInsertedFromPropsFile) {
    this.propToBeInsertedFromPropsFile = propToBeInsertedFromPropsFile;
  }

  private String propToBeInsertedFromPropsFile;

  public String getPropToBeInsertedFromXml() {
    return propToBeInsertedFromXml;
  }

  public String getPropToBeInsertedFromPropsFile() {
    return propToBeInsertedFromPropsFile;
  }

  //
  //  public MySampleBean(String propToBeInsertedFromXml,
  //      String prodToBeInsertedFromPropsFile) {
  //    this.propToBeInsertedFromXml = propToBeInsertedFromXml;
  //    this.propToBeInsertedFromPropsFile = prodToBeInsertedFromPropsFile;
  //  }

}
