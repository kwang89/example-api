package com.example.api.global.exception;

import org.springframework.http.HttpStatus;

import com.example.api.global.code.GlobalErrorCode;
import com.example.api.global.exception.base.BaseException;

import lombok.Getter;

/**
 * BadRequestException
 * <p>
 * HttpStatusCode : 400 - BadRequest
 * 
 * @author sk.kwon
 */
@Getter
public class BadRequestException extends BaseException {

  private Object data;

  public BadRequestException(GlobalErrorCode errorCode) {
    super(HttpStatus.BAD_REQUEST, errorCode);
  }

  public BadRequestException(GlobalErrorCode errorCode, Object data) {
    super(HttpStatus.BAD_REQUEST, errorCode);
    this.data = data;
  }

  public BadRequestException(GlobalErrorCode errorCode, Exception e) {
    super(HttpStatus.BAD_REQUEST, errorCode, e);
  }

  public BadRequestException(GlobalErrorCode errorCode, Exception e, Object data) {
    super(HttpStatus.BAD_REQUEST, errorCode, e);
    this.data = data;
  }
}
