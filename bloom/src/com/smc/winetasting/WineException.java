package com.smc.winetasting;

public class WineException extends Exception {

  private static final long serialVersionUID = 1L;
  private ErrorCodes        errorCode;

  public WineException(String message, Throwable cause) {
    super(message, cause);
  }

  public WineException(String message) {
    super(message);
  }

  public WineException(ErrorCodes errorCode) {
    this.errorCode = errorCode;
  }

  public ErrorCodes getErrorCode() {
    return errorCode;
  }

}
