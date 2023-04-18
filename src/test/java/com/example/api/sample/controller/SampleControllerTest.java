package com.example.api.sample.controller;

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.*;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.*;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.MockMvc;

@ExtendWith(RestDocumentationExtension.class)
@AutoConfigureRestDocs
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = RANDOM_PORT)
class SampleControllerTest {
	@Autowired
	private MockMvc mockMvc;

	@Test
	void restdoc_생성_테스트() throws Exception {
		mockMvc.perform(get("/exception").accept(MediaType.APPLICATION_JSON)) // (1)
			.andExpect(status().is5xxServerError()) // (2)
			.andDo(
				document("sample/exception"
					, responseFields(
						fieldWithPath("errorCode").description("에러코드"),
						fieldWithPath("errorMessage").description("에러메세지"),
						fieldWithPath("message").description("메세지"),
						fieldWithPath("errors").description("메세지").optional().type(JsonFieldType.ARRAY),
						fieldWithPath("data").description("메세지").optional().type(JsonFieldType.OBJECT)
					)
				)
			); // (3)
	}

	//
	// @Test
	// public void application_form_전송() {
	// 	String content = "<li>content</li>";
	// 	String expected = "&lt;li&gt;content&lt;/li&gt;";
	// 	HttpHeaders headers = new HttpHeaders();
	// 	headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
	// 	MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
	// 	map.add("content", content);
	//
	// 	HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(map, headers);
	//
	// 	ResponseEntity<String> response = restTemplate.exchange("/form",
	// 		HttpMethod.POST,
	// 		entity,
	// 		String.class);
	//
	// 	assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
	// 	assertThat(response.getHeaders().getContentType().toString()).isEqualTo("text/plain;charset=UTF-8");
	// 	assertThat(response.getBody()).isEqualTo(content);
	// }
	//
	// @Test
	// public void 태그_치환() {
	// 	String content = "<li>content</li>";
	// 	String expected = "&lt;li&gt;content&lt;/li&gt;";
	// 	ExampleDto dto = new ExampleDto();
	// 	dto.setContent(content);
	//
	// 	ResponseEntity<ExampleDto> response = restTemplate.postForEntity(
	// 		"/xss",
	// 		dto,
	// 		ExampleDto.class);
	// 	assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
	// 	assertThat(response.getBody().getContent()).isEqualTo(expected);
	// }
}




