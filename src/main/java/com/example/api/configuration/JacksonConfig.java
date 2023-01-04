package com.example.api.configuration;

import java.util.Locale;
import java.util.TimeZone;

import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

/**
 * <pre>
 * ObjectMapper 및 POJO to JSON, JSON to POJO 시 적용할 config
 * </pre>
 * 
 * @author sk.kwon
 */
@Configuration
public class JacksonConfig {
  /**
   * Json Serialize/Deserialize 설정
   * <p>
   * ObjectMapper 및 POJO to JSON, JSON to POJO
   *
   * @return Jackson2ObjectMapperBuilderCustomizer
   */
  @Bean
  public Jackson2ObjectMapperBuilderCustomizer jsonCustomizer() {
    return builder -> builder
        .serializationInclusion(JsonInclude.Include.ALWAYS)
        .featuresToDisable(
            SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
        .timeZone(TimeZone.getDefault())
        .locale(Locale.getDefault())
        .modulesToInstall(new JavaTimeModule());
  }

}
