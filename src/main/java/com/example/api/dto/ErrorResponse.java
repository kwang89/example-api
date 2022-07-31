package com.example.api.dto;

import java.util.List;

import com.example.api.global.code.GlobalErrorCode;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Error Reponse format
 * 
 * @author sk.kwon
 */

@Builder
@AllArgsConstructor
@Setter
@Getter
@NoArgsConstructor
public class ErrorResponse {
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

  public ErrorResponse(GlobalErrorCode errorCode) {
    this.errorCode = errorCode.getErrorCode();
    this.errorMessage = errorCode.getErrorMessage();
  }

  public ErrorResponse(GlobalErrorCode errorCode, String message) {
    this(errorCode);
    this.message = message;
  }
}
