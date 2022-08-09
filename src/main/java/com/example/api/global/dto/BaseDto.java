package com.example.api.global.dto;

import com.example.api.global.masking.MaskingToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class BaseDto {
  @Override
  public String toString() {
    return new MaskingToStringBuilder(this, ToStringStyle.JSON_STYLE).toString();
  }
}
