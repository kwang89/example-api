package com.example.project.global.dto;

import com.example.project.global.code.base.BaseErrorCode;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Error Reponse format
 *
 * @author sk.kwon
 */
@Setter
@Getter
@NoArgsConstructor
public class ErrorResponse extends BaseDto {

    /**
     * 에러코드
     */
    private BaseErrorCode errorCode;
    /**
     * validation 에러 목록(Optional)
     */
    private List<String> errors;
    /**
     * data(Optional)
     */
    private Object data;

    @JsonIgnore
    private Object[] messageArgs;

    public ErrorResponse(BaseErrorCode errorCode) {
        this.errorCode = errorCode;
    }
    public ErrorResponse(BaseErrorCode errorCode, Object[] messageArgs) {
        this.errorCode = errorCode;
        this.messageArgs = messageArgs;
    }

    @Builder
    public ErrorResponse(BaseErrorCode errorCode, List<String> errors,
        Object data) {
        this.errorCode = errorCode;
        this.errors = errors;
        this.data = data;
    }
}
