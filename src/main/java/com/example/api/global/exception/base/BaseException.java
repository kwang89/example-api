package com.example.api.global.exception.base;

import org.springframework.http.HttpStatus;

import com.example.api.global.code.GlobalErrorCode;
import com.example.api.global.exception.ApiException;

import lombok.Getter;

/**
 * {@code BaseException} 클래스를 상속받아서 CustomException을 정의
 *
 * @author sk.kwon
 * @see ApiException
 */

@Getter
public class BaseException extends RuntimeException {
  private final HttpStatus httpStatus;
  private final GlobalErrorCode errorCode;

  public BaseException(GlobalErrorCode errorCode) {
    this(HttpStatus.INTERNAL_SERVER_ERROR, errorCode);
  }
  public BaseException(GlobalErrorCode errorCode, Exception e) {
    this(HttpStatus.INTERNAL_SERVER_ERROR, errorCode, e);
  }

  public BaseException(int statusCode, GlobalErrorCode errorCode) {
    this(HttpStatus.valueOf(statusCode), errorCode);
  }

  public BaseException(int statusCode, GlobalErrorCode errorCode, Exception e) {
    super(e);
    this.httpStatus = HttpStatus.valueOf(statusCode);
    this.errorCode = errorCode;
  }

  public BaseException(HttpStatus httpStatus, GlobalErrorCode errorCode) {
    super(errorCode.getErrorMessage());
    this.httpStatus = httpStatus;
    this.errorCode = errorCode;
  }

  public BaseException(HttpStatus httpStatus, GlobalErrorCode errorCode, Exception e) {
    super(e);
    this.httpStatus = httpStatus;
    this.errorCode = errorCode;
  }
}
