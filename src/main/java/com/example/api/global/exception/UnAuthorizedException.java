package com.example.api.global.exception;

import org.springframework.http.HttpStatus;

import com.example.api.global.code.base.BaseErrorCode;
import com.example.api.global.exception.base.BaseException;

import lombok.Getter;

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

	public UnAuthorizedException(BaseErrorCode errorCode, Exception exception) {
		super(HttpStatus.UNAUTHORIZED, errorCode, exception);
	}

	public UnAuthorizedException(BaseErrorCode errorCode, Exception exception, Object data) {
		super(HttpStatus.UNAUTHORIZED, errorCode, exception);
		this.data = data;
	}
}
