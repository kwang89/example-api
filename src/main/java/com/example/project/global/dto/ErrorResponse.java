package com.example.project.global.dto;

import com.example.project.global.code.base.BaseErrorCode;
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
    private String errorCode;
    /**
     * 화면에 표시될 메시지
     */
    private String errorMessage;
    /**
     * validation 에러 목록(Optional)
     */
    private List<String> errors;
    /**
     * data(Optional)
     */
    private Object data;

    public ErrorResponse(BaseErrorCode errorCode) {
        this.errorCode = errorCode.getErrorCode();
    }

    public ErrorResponse(BaseErrorCode errorCode, String errorMessage) {
        this(errorCode);
        this.errorMessage = errorMessage;
    }

    @Builder
    public ErrorResponse(String errorCode, String errorMessage, List<String> errors,
        Object data) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
        this.errors = errors;
        this.data = data;
    }
}
