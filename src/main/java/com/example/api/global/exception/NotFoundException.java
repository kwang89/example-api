package com.example.api.global.exception;

import org.springframework.http.HttpStatus;

import com.example.api.global.code.base.BaseErrorCode;
import com.example.api.global.exception.base.BaseException;

import lombok.Getter;

/**
 * NotFoundException
 * <p>
 * HttpStatusCode : 404 - Not Found
 *
 * @author sk.kwon
 */
@Getter
public class NotFoundException extends BaseException {

	private Object data;

	public NotFoundException(BaseErrorCode errorCode) {
		super(HttpStatus.NOT_FOUND, errorCode);
	}

	public NotFoundException(BaseErrorCode errorCode, Object data) {
		super(HttpStatus.NOT_FOUND, errorCode);
		this.data = data;
	}

	public NotFoundException(BaseErrorCode errorCode, Exception e) {
		super(HttpStatus.NOT_FOUND, errorCode, e);
	}

	public NotFoundException(BaseErrorCode errorCode, Exception e, Object data) {
		super(HttpStatus.NOT_FOUND, errorCode, e);
		this.data = data;
	}
}
