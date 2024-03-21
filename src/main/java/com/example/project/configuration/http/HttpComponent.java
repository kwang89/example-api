package com.example.project.configuration.http;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@ConfigurationProperties(prefix = "example.http.http-component")
@Configuration
public class HttpComponent {
    private int connectionTimeout;
    private int connectionRequestTimeout;
    private int socketTimeout;
    private int readTimeout;
    private int maxConnTotal;
    private int maxConnPerRoute;
    private int keepAlive;

}
