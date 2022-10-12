package com.example.api.global.exception;

import com.example.api.global.code.base.BaseErrorCode;
import com.example.api.global.exception.base.BaseException;
import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 * ConflictException
 * <p>
 * HttpStatusCode : 409 - Conflict
 *
 * @author sk.kwon
 */
@Getter
public class ConflictException extends BaseException {

  private Object data;

  public ConflictException(BaseErrorCode errorCode) {
    super(HttpStatus.CONFLICT, errorCode);
  }

  public ConflictException(BaseErrorCode errorCode, Object data) {
    super(HttpStatus.CONFLICT, errorCode);
    this.data = data;
  }

  public ConflictException(BaseErrorCode errorCode, Exception e) {
    super(HttpStatus.CONFLICT, errorCode, e);
  }

  public ConflictException(BaseErrorCode errorCode, Exception e, Object data) {
    super(HttpStatus.CONFLICT, errorCode, e);
    this.data = data;
  }
}
