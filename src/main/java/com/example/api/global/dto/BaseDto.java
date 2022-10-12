package com.example.api.global.dto;

import com.example.api.global.masking.MaskingToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

// TODO apache commons 제거 후 guava로 변경하여 toString 설정
public class BaseDto {
  @Override
  public String toString() {
    return new MaskingToStringBuilder(this, ToStringStyle.JSON_STYLE).toString();
  }
}
