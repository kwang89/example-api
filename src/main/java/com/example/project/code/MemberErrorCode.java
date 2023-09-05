package com.example.project.code;

import com.example.project.global.code.base.BaseErrorCode;
import lombok.Getter;

@Getter
public enum MemberErrorCode implements BaseErrorCode {

    MEMBER_NOT_FOUND("error-100001", "Member Not Found");

    private final String errorCode;
    private final String errorDescription;

    MemberErrorCode(String errorCode, String errorDescription) {
        this.errorCode = errorCode;
        this.errorDescription = errorDescription;
    }
}
