package com.example.project.global.exception;

import com.example.project.global.code.base.BaseErrorCode;
import com.example.project.global.exception.base.BaseException;
import lombok.Getter;
import org.springframework.http.HttpStatus;

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

    public ForbiddenException(BaseErrorCode errorCode, Exception exception) {
        super(HttpStatus.FORBIDDEN, errorCode, exception);
    }

    public ForbiddenException(BaseErrorCode errorCode, Exception exception, Object data) {
        super(HttpStatus.FORBIDDEN, errorCode, exception);
        this.data = data;
    }
}
