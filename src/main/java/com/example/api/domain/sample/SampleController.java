package com.example.api.domain.sample;

import com.example.api.dto.ExampleDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.api.global.code.GlobalErrorCode;
import com.example.api.global.exception.ApiException;
@Slf4j
@RestController
public class SampleController {
  @GetMapping("/exception")
  public void error() {
    throw new ApiException(GlobalErrorCode.SAMPLE_ERROR);
  }

  @GetMapping("/tostring")
  public ExampleDto toStr() {
    ExampleDto dto = new ExampleDto();
    dto.setAge(1);
    dto.setLongData(5L);
    dto.setTestNm("testNm");

    log.info(dto.toString());
    return dto;
  }
}
