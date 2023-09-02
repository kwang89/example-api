package com.example.api.global.exception;

import com.example.api.global.code.base.BaseErrorCode;
import com.example.api.global.exception.base.BaseException;
import lombok.Getter;
import org.springframework.http.HttpStatus;

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

    public InternalServerErrorException(BaseErrorCode errorCode, Exception exception) {
        super(HttpStatus.INTERNAL_SERVER_ERROR, errorCode, exception);
    }

    public InternalServerErrorException(BaseErrorCode errorCode, Exception exception, Object data) {
        super(HttpStatus.INTERNAL_SERVER_ERROR, errorCode, exception);
        this.data = data;
    }
}
