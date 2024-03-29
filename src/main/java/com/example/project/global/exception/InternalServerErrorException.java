package com.example.project.global.exception;

import com.example.project.global.code.base.BaseErrorCode;
import com.example.project.global.exception.base.BaseException;
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
    private Object[] messageArgs;

    public InternalServerErrorException(BaseErrorCode errorCode) {
        super(HttpStatus.INTERNAL_SERVER_ERROR, errorCode);
    }

    public InternalServerErrorException(BaseErrorCode errorCode, Object... messageArgs) {
        super(HttpStatus.INTERNAL_SERVER_ERROR, errorCode);
        this.messageArgs = messageArgs;
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
