package com.example.project.configuration;

import com.example.project.configuration.jackson.module.EnumModule;
import com.example.project.configuration.xss.HtmlCharacterEscapes;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

@RequiredArgsConstructor
@Configuration
public class WebMvcConfig {

    private final ObjectMapper objectMapper;
    private final MessageSourceAccessor messageSourceAccessor;

    @Bean
    public MappingJackson2HttpMessageConverter jsonEscapeConverter() {
        ObjectMapper copy = objectMapper.copy();
        copy.getFactory().setCharacterEscapes(new HtmlCharacterEscapes());
        copy.registerModule(new EnumModule(messageSourceAccessor));
        return new MappingJackson2HttpMessageConverter(copy);
    }
}
