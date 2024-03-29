package com.example.project.global.code;

import com.example.project.global.code.base.BaseErrorCode;
import com.example.project.global.exception.handler.GlobalExceptionHandler;
import lombok.Getter;

/**
 * 프로젝트에 공통 에러코드 Enum
 *
 * @author sk.kwon
 * @see GlobalExceptionHandler
 */
@Getter
public enum GlobalErrorCode implements BaseErrorCode {

    UNKNOWN_ERROR("error-000001", "Unknown Error"),
    HTTP_REQUEST_METHOD_NOT_SUPPORTED("error-000002", "Http Request Method Not Supproted"),
    HTTP_MEDIA_TYPE_NOT_SUPPORTED("error-000003", "Http Media Type Not Supproted"),
    HTTP_MEDIA_TYPE_NOT_ACCEPTABLE("error-000004", "Http Media Type Not Acceptable"),
    MISSING_PATH_VARIABLE("error-000005", "Missing Path Variable"),
    MISSING_SERVLET_REQUEST_PARAMETER("error-000006", "Missing Servlet Request Parameter"),
    SERVLET_REQUEST_BINDING("ERROR-000007", "Servlet Request Binding"),
    CONVERSION_NOT_SUPPORTED("error-000008", "Conversion Not Supported"), TYPE_MISMATCH(
        "error-000009",
        "Type Mismatch"),
    HTTP_MESSAGE_NOT_READABLE("error-000010", "Http Message Not Readable"),
    HTTP_MESSAGE_NOT_WRITABLE("error-000011", "Http Message Not Writable"),
    METHOD_ARGUMENT_NOT_VALID("error-000012", "Method Argument Not Valid"),
    MISSING_SERVLET_REQUEST_PART("error-000013", "Missing Servlet Request Part"),
    BIND_ERROR("error-000014", "Bind Error"),
    NO_HANDLER_FOUND("error-000015", "Api Not Found"),
    ASYNC_REQUEST_TIMEOUT("error-000016", "Async Request Timeout"),
    ILLEGAL_ACCESS("error-000017", "Illegal Access"),
    SAMPLE_ERROR("sample-000001", "sample error");

    private final String errorCode;
    private final String errorDescription;

    GlobalErrorCode(String errorCode, String errorDescription) {
        this.errorCode = errorCode;
        this.errorDescription = errorDescription;
    }
}
