package com.example.project.global.exception;

import com.example.project.global.code.base.BaseErrorCode;
import com.example.project.global.exception.base.BaseException;
import lombok.Getter;
import org.springframework.http.HttpStatus;

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

    public BadRequestException(BaseErrorCode errorCode, Exception exception) {
        super(HttpStatus.BAD_REQUEST, errorCode, exception);
    }

    public BadRequestException(BaseErrorCode errorCode, Exception exception, Object data) {
        super(HttpStatus.BAD_REQUEST, errorCode, exception);
        this.data = data;
    }
}
