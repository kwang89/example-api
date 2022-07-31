package com.example.api.global.exception;

import org.springframework.http.HttpStatus;

import com.example.api.global.code.GlobalErrorCode;
import com.example.api.global.exception.base.BaseException;

import lombok.Getter;

/**
 * CustomException : {@code ErrorCode}를 사용하여 정의
 * 
 * @author sk.kwon
 */
@Getter
public class ApiException extends BaseException {

  private Object data;

  public ApiException(GlobalErrorCode errorCode) {
    super(errorCode);
  }

  public ApiException(GlobalErrorCode errorCode, Object data) {
    super(errorCode);
    this.data = data;
  }

  public ApiException(int statusCode, GlobalErrorCode errorCode) {
    super(statusCode, errorCode);
  }

  public ApiException(int statusCode, GlobalErrorCode errorCode, Object data) {
    super(statusCode, errorCode);
    this.data = data;
  }

  public ApiException(int statusCode, GlobalErrorCode errorCode, Exception e) {
    super(statusCode, errorCode, e);
  }

  public ApiException(int statusCode, GlobalErrorCode errorCode, Exception e, Object data) {
    super(statusCode, errorCode, e);
    this.data = data;
  }

  public ApiException(HttpStatus httpStatus, GlobalErrorCode errorCode) {
    super(httpStatus, errorCode);
  }

  public ApiException(HttpStatus httpStatus, GlobalErrorCode errorCode, Object data) {
    super(httpStatus, errorCode);
    this.data = data;
  }

  public ApiException(HttpStatus httpStatus, GlobalErrorCode errorCode, Exception e) {
    super(httpStatus, errorCode, e);
  }

  public ApiException(HttpStatus httpStatus, GlobalErrorCode errorCode, Exception e, Object data) {
    super(httpStatus, errorCode, e);
    this.data = data;
  }

}
