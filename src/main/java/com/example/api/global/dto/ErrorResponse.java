package com.example.api.global.dto;

import java.util.List;

import com.example.api.global.code.base.BaseErrorCode;
import com.example.api.global.dto.BaseDto;
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
  /** 에러코드 */
  private String errorCode;
  /** 에러메시지 */
  private String errorMessage;
  /** 화면에 표시될 메시지 */
  private String message;
  /** validation 에러 목록(Optional) */
  private List<String> errors;
  /** data(Optional) */
  private Object data;

  public ErrorResponse(BaseErrorCode errorCode) {
    this.errorCode = errorCode.getErrorCode();
    this.errorMessage = errorCode.getErrorMessage();
  }

  public ErrorResponse(BaseErrorCode errorCode, String message) {
    this(errorCode);
    this.message = message;
  }
  @Builder
  public ErrorResponse(String errorCode, String errorMessage, String message, List<String> errors, Object data) {
    this.errorCode = errorCode;
    this.errorMessage = errorMessage;
    this.message = message;
    this.errors = errors;
    this.data = data;
  }
}
