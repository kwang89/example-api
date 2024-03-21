package com.example.project.configuration.http;

import static org.springframework.core.io.support.ResourcePatternUtils.*;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.util.Set;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.stereotype.Component;
import org.springframework.util.ClassUtils;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.support.RestClientAdapter;
import org.springframework.web.service.annotation.HttpExchange;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

import com.example.project.global.annotation.http.HttpExchangeInterface;
import com.google.common.reflect.ClassPath;

import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Component
public class HttpExchnagePostProcessor implements BeanFactoryPostProcessor {
    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {

        // RestClient restClient = restClientBuilder.baseUrl("http://localhost:8081").build();
        //
        // HttpServiceProxyFactory httpServiceProxyFactory = HttpServiceProxyFactory.builderFor(RestClientAdapter.create(restClient)).build();
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        ClassPath classPath = null;
        try {
            classPath = ClassPath.from(classLoader);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // https://chanyongmoon.tistory.com/8
        // https://myvelop.tistory.com/217#RestClient%EB%9E%80%3F-1
        // https://docs.spring.io/spring-framework/reference/integration/rest-clients.html#rest-http-interface

        // 지정된 패키지 내의 클래스 정보 출력
        for (ClassPath.ClassInfo classInfo : classPath.getTopLevelClasses("com.example.project.client")) {
            Class<?> clazz = classInfo.load();
            if (clazz.isAnnotationPresent(HttpExchangeInterface.class)) {
                System.out.println("Class: " + clazz.getName() + " has annotation: " + HttpExchangeInterface.class.getSimpleName());
            }
        }


    }
}
