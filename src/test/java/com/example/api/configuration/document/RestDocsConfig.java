package com.example.api.configuration.document;

import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;

import org.springframework.boot.test.autoconfigure.restdocs.RestDocsMockMvcConfigurationCustomizer;
import org.springframework.context.annotation.Configuration;
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentationConfigurer;
import org.springframework.restdocs.operation.preprocess.Preprocessors;

@Configuration(proxyBeanMethods = false)
public class RestDocsConfig implements RestDocsMockMvcConfigurationCustomizer {
    @Override
    public void customize(MockMvcRestDocumentationConfigurer configurer) {
        configurer.operationPreprocessors().withRequestDefaults(prettyPrint())
            .withResponseDefaults(prettyPrint());
    }
}
