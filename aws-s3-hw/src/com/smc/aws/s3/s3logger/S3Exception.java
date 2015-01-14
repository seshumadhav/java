package com.smc.aws.s3.s3logger;

public class S3Exception extends Exception {

  private static final long serialVersionUID = 1L;

  public S3Exception() {
	super();
  }

  public S3Exception(String message, Throwable cause) {
	super(message, cause);
  }

  public S3Exception(String message) {
	super(message);
  }

}
