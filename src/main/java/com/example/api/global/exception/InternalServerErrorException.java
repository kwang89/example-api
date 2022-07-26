package com.example.api.global.exception;

import com.example.api.global.code.base.BaseErrorCode;
import org.springframework.http.HttpStatus;

import com.example.api.global.exception.base.BaseException;

import lombok.Getter;

/**
 * InternalServerErrorException
 * <p>
 * HttpStatusCode : 500 - InternalServerError
 *
 * @author sk.kwon
 */
@Getter
public class InternalServerErrorException extends BaseException {

  private Object data;

  public InternalServerErrorException(BaseErrorCode errorCode) {
    super(HttpStatus.INTERNAL_SERVER_ERROR, errorCode);
  }

  public InternalServerErrorException(BaseErrorCode errorCode, Object data) {
    super(HttpStatus.INTERNAL_SERVER_ERROR, errorCode);
    this.data = data;
  }

  public InternalServerErrorException(BaseErrorCode errorCode, Exception e) {
    super(HttpStatus.INTERNAL_SERVER_ERROR, errorCode, e);
  }

  public InternalServerErrorException(BaseErrorCode errorCode, Exception e, Object data) {
    super(HttpStatus.INTERNAL_SERVER_ERROR, errorCode, e);
    this.data = data;
  }
}
