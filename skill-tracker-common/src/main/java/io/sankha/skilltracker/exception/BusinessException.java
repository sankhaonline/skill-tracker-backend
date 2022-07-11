package io.sankha.skilltracker.exception;

public class BusinessException extends RuntimeException {
  public BusinessException() {
    super();
  }

  public BusinessException(Throwable cause) {
    super(cause);
  }

  public BusinessException(String errorMessage, Throwable err) {
    super(errorMessage, err);
  }

  public BusinessException(String errorMessage) {
    super(errorMessage);
  }
}
