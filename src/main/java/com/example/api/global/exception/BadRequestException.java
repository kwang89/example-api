package com.example.api.global.exception;

import org.springframework.http.HttpStatus;

import com.example.api.global.code.base.BaseErrorCode;
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

	public BadRequestException(BaseErrorCode errorCode) {
		super(HttpStatus.BAD_REQUEST, errorCode);
	}

	public BadRequestException(BaseErrorCode errorCode, Object data) {
		super(HttpStatus.BAD_REQUEST, errorCode);
		this.data = data;
	}

	public BadRequestException(BaseErrorCode errorCode, Exception e) {
		super(HttpStatus.BAD_REQUEST, errorCode, e);
	}

	public BadRequestException(BaseErrorCode errorCode, Exception e, Object data) {
		super(HttpStatus.BAD_REQUEST, errorCode, e);
		this.data = data;
	}
}
