package com.example.api.global.exception;

import com.example.api.global.code.base.BaseErrorCode;
import com.example.api.global.exception.base.BaseException;
import lombok.Getter;
import org.springframework.http.HttpStatus;

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

    public NotFoundException(BaseErrorCode errorCode, Exception exception) {
        super(HttpStatus.NOT_FOUND, errorCode, exception);
    }

    public NotFoundException(BaseErrorCode errorCode, Exception exception, Object data) {
        super(HttpStatus.NOT_FOUND, errorCode, exception);
        this.data = data;
    }
}
