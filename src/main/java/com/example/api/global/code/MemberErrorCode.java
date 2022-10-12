package com.example.api.global.code;

import com.example.api.global.code.base.BaseErrorCode;
import lombok.Getter;

@Getter
public enum MemberErrorCode implements BaseErrorCode {

  MEMBER_NOT_FOUND("error-100001", "Member Not Found");

  private final String errorCode;
  private final String errorMessage;

  MemberErrorCode(String errorCode, String errorMessage) {
    this.errorCode = errorCode;
    this.errorMessage = errorMessage;
  }
}
