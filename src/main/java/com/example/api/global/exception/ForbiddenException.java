package com.example.api.global.exception;

import org.springframework.http.HttpStatus;

import com.example.api.global.code.base.BaseErrorCode;
import com.example.api.global.exception.base.BaseException;

import lombok.Getter;

/**
 * ForbiddenException
 * <p>
 * HttpStatusCode : 403 - Forbidden
 *
 * @author sk.kwon
 */
@Getter
public class ForbiddenException extends BaseException {

	private Object data;

	public ForbiddenException(BaseErrorCode errorCode) {
		super(HttpStatus.FORBIDDEN, errorCode);
	}

	public ForbiddenException(BaseErrorCode errorCode, Object data) {
		super(HttpStatus.FORBIDDEN, errorCode);
		this.data = data;
	}

	public ForbiddenException(BaseErrorCode errorCode, Exception e) {
		super(HttpStatus.FORBIDDEN, errorCode, e);
	}

	public ForbiddenException(BaseErrorCode errorCode, Exception e, Object data) {
		super(HttpStatus.FORBIDDEN, errorCode, e);
		this.data = data;
	}
}
