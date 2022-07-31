package com.example.api.domain.sample;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.api.global.code.GlobalErrorCode;
import com.example.api.global.exception.ApiException;

@RestController
public class SampleController {
  @GetMapping("/exception")
  public void error() {
    throw new ApiException(GlobalErrorCode.SAMPLE_ERROR);
  }
}
