package com.example.project.global.code.base;

import com.example.project.global.code.GlobalErrorCode;

/**
 * ErrorCode enum 생성 시, 아래 interface를 구현
 *
 * @author sk.kwon
 * @see GlobalErrorCode
 */
public interface BaseErrorCode {

    String getErrorCode();

    String getErrorDescription();
}
