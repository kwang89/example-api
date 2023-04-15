package com.example.api.sample.controller;

import static org.assertj.core.api.AssertionsForClassTypes.*;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import com.example.api.sample.dto.ExampleDto;

@SpringBootTest(webEnvironment = RANDOM_PORT)
	//@WebMvcTest(SampleController.class)
class SampleControllerTest {
	//  @Autowired
	//  private MockMvc mvc;

	@Autowired
	private TestRestTemplate restTemplate;

	@Test
	public void application_form_전송() {
		String content = "<li>content</li>";
		String expected = "&lt;li&gt;content&lt;/li&gt;";
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
		MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
		map.add("content", content);

		HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(map, headers);

		ResponseEntity<String> response = restTemplate.exchange("/form",
			HttpMethod.POST,
			entity,
			String.class);

		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(response.getHeaders().getContentType().toString()).isEqualTo("text/plain;charset=UTF-8");
		assertThat(response.getBody()).isEqualTo(content);
	}

	@Test
	public void 태그_치환() {
		String content = "<li>content</li>";
		String expected = "&lt;li&gt;content&lt;/li&gt;";
		ExampleDto dto = new ExampleDto();
		dto.setContent(content);

		ResponseEntity<ExampleDto> response = restTemplate.postForEntity(
			"/xss",
			dto,
			ExampleDto.class);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(response.getBody().getContent()).isEqualTo(expected);
	}
}




