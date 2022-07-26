package com.example.api.global.exception.base;

import com.example.api.global.code.base.BaseErrorCode;
import org.springframework.http.HttpStatus;

import com.example.api.global.exception.InternalServerErrorException;

import lombok.Getter;

/**
 * {@code BaseException} 클래스를 상속받아서 CustomException을 정의
 *
 * @author sk.kwon
 * @see InternalServerErrorException
 */

@Getter
public class BaseException extends RuntimeException {
  private final HttpStatus httpStatus;
  private final BaseErrorCode errorCode;

  public BaseException(BaseErrorCode errorCode) {
    this(HttpStatus.INTERNAL_SERVER_ERROR, errorCode);
  }
  public BaseException(BaseErrorCode errorCode, Exception e) {
    this(HttpStatus.INTERNAL_SERVER_ERROR, errorCode, e);
  }

  public BaseException(int statusCode, BaseErrorCode errorCode) {
    this(HttpStatus.valueOf(statusCode), errorCode);
  }

  public BaseException(int statusCode, BaseErrorCode errorCode, Exception e) {
    super(e);
    this.httpStatus = HttpStatus.valueOf(statusCode);
    this.errorCode = errorCode;
  }

  public BaseException(HttpStatus httpStatus, BaseErrorCode errorCode) {
    super(errorCode.getErrorMessage());
    this.httpStatus = httpStatus;
    this.errorCode = errorCode;
  }

  public BaseException(HttpStatus httpStatus, BaseErrorCode errorCode, Exception e) {
    super(e);
    this.httpStatus = httpStatus;
    this.errorCode = errorCode;
  }
}
