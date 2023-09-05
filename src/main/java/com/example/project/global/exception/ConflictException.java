package com.example.project.global.exception;

import com.example.project.global.code.base.BaseErrorCode;
import com.example.project.global.exception.base.BaseException;
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

    public ConflictException(BaseErrorCode errorCode, Exception exception) {
        super(HttpStatus.CONFLICT, errorCode, exception);
    }

    public ConflictException(BaseErrorCode errorCode, Exception exception, Object data) {
        super(HttpStatus.CONFLICT, errorCode, exception);
        this.data = data;
    }
}
