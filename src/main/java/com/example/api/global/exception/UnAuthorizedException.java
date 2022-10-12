package com.example.api.global.exception;

import com.example.api.global.code.base.BaseErrorCode;
import com.example.api.global.exception.base.BaseException;
import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 * UnAuthorizedException
 * <p>
 * HttpStatusCode : 401 - UnAuthorized
 *
 * @author sk.kwon
 */
@Getter
public class UnAuthorizedException extends BaseException {

  private Object data;

  public UnAuthorizedException(BaseErrorCode errorCode) {
    super(HttpStatus.UNAUTHORIZED, errorCode);
  }

  public UnAuthorizedException(BaseErrorCode errorCode, Object data) {
    super(HttpStatus.UNAUTHORIZED, errorCode);
    this.data = data;
  }

  public UnAuthorizedException(BaseErrorCode errorCode, Exception e) {
    super(HttpStatus.UNAUTHORIZED, errorCode, e);
  }

  public UnAuthorizedException(BaseErrorCode errorCode, Exception e, Object data) {
    super(HttpStatus.UNAUTHORIZED, errorCode, e);
    this.data = data;
  }
}
