package com.example.api.configuration;

import java.sql.Time;
import java.util.Locale;
import java.util.TimeZone;

import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

@Configuration
public class JacksonConfig {
  /**
   * null 제외
   * Collection : isEmpty true 제외 
   * Array : size() == 0 제외
   * String : length == 0 제외
   * 
   * @return Jackson2ObjectMapperBuilderCustomizer
   */
  @Bean
  public Jackson2ObjectMapperBuilderCustomizer jsonCustomizer() {
    return builder -> builder
    .serializationInclusion(JsonInclude.Include.NON_EMPTY)
    .featuresToDisable(
      SerializationFeature.WRITE_DATES_AS_TIMESTAMPS
      , DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES
    )
    .timeZone(TimeZone.getDefault())
    .locale(Locale.getDefault())
    .modulesToInstall(new JavaTimeModule());
  }
    
}
