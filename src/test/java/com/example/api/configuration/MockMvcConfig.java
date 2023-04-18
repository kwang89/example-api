package com.example.api.configuration;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;

import java.nio.charset.StandardCharsets;

import org.springframework.boot.test.autoconfigure.web.servlet.MockMvcBuilderCustomizer;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.web.servlet.setup.ConfigurableMockMvcBuilder;

@Configuration(proxyBeanMethods = false)
public class MockMvcConfig implements MockMvcBuilderCustomizer {

	@Override
	public void customize(ConfigurableMockMvcBuilder<?> builder) {
		builder.defaultResponseCharacterEncoding(StandardCharsets.UTF_8)
			.alwaysDo(print());
	}
}
