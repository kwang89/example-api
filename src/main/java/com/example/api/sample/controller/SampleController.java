package com.example.api.sample.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.api.global.code.GlobalErrorCode;
import com.example.api.global.exception.InternalServerErrorException;
import com.example.api.sample.dto.ExampleDto;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@RestController
public class SampleController {
	@GetMapping("/exception")
	public void error() {
		throw new InternalServerErrorException(GlobalErrorCode.SAMPLE_ERROR);
	}

	@PostMapping("/xss")
	public ExampleDto xss(@RequestBody ExampleDto dto) {
		log.debug("xss controller");
		return dto;
	}

	@PostMapping("/form")
	public String xssForm(ExampleDto dto) {
		log.debug("xss form controller : {}", dto.getContent());
		return dto.getContent();
	}

	@GetMapping("/tostring")
	public ExampleDto toStr() {
		ExampleDto dto = new ExampleDto();
		dto.setAge(1);
		dto.setLongData(5L);
		dto.setTestNm("testNm");

		log.info(dto.toString());
		log.info("test : {}", dto);

		return dto;
	}
}
