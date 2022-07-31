package com.example.api.global.code.base;

import com.example.api.global.code.GlobalErrorCode;

/**
 * ErrorCode enum 생성 시, 아래 interface를 구현
 * 
 * @see GlobalErrorCode
 * 
 * @author sk.kwon
 */
public interface BaseErrorCode {
  String getErrorCode();

  String getErrorMessage();
}
