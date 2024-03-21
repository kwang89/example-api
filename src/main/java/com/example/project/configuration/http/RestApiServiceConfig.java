package com.example.project.configuration.http;

import java.util.List;

import org.apache.hc.client5.http.config.ConnectionConfig;
import org.apache.hc.client5.http.config.RequestConfig;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClientBuilder;
import org.apache.hc.client5.http.impl.io.PoolingHttpClientConnectionManager;
import org.apache.hc.client5.http.impl.io.PoolingHttpClientConnectionManagerBuilder;
import org.apache.hc.core5.pool.PoolConcurrencyPolicy;
import org.apache.hc.core5.pool.PoolReusePolicy;
import org.apache.hc.core5.util.TimeValue;
import org.apache.hc.core5.util.Timeout;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.web.client.RestClientBuilderConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestClient;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Configuration
public class RestApiServiceConfig {
    private final HttpComponent hcConfig;
    // private final ObjectMapper objectMapper;

    @Bean
    public RestClient.Builder restClientBuilder(
        RestClientBuilderConfigurer configurer,
        CloseableHttpClient httpClient,
        List<ClientHttpRequestInterceptor> requestInterceptorList
    ) {
        return configurer.configure(RestClient.builder())
            .requestFactory(new HttpComponentsClientHttpRequestFactory(httpClient))
            .requestInterceptors(interceptors -> interceptors.addAll(requestInterceptorList))
            // .defaultStatusHandler(HttpStatusCode::isError, (request, response) -> {
            //     throw new ExternalRequestException(
            //         HttpStatus.valueOf(response.getStatusCode().value()),
            //         HTTP_REQUEST_FAILURE,
            //         objectMapper.readValue(response.getBody(), Map.class)
            //     );
            // })
            .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
            ;
    }


    @Bean
    public RestClient restClient(RestClient.Builder restClientBuilder) {
        return restClientBuilder.build();
    }

    @ConditionalOnMissingBean(PoolingHttpClientConnectionManager.class)
    @Bean
    public PoolingHttpClientConnectionManager poolingConnectionManager() {
        return PoolingHttpClientConnectionManagerBuilder.create().setPoolConcurrencyPolicy(PoolConcurrencyPolicy.STRICT)
            .setConnPoolPolicy(PoolReusePolicy.LIFO)
            .setMaxConnTotal(hcConfig.getMaxConnTotal())
            .setMaxConnPerRoute(hcConfig.getMaxConnPerRoute())
            .setDefaultConnectionConfig(ConnectionConfig.custom()
                .setConnectTimeout(Timeout.ofMilliseconds(hcConfig.getConnectionTimeout()))
                .setSocketTimeout(Timeout.ofMilliseconds(hcConfig.getSocketTimeout()))
                .build())
            .build();
    }

    @ConditionalOnMissingBean(CloseableHttpClient.class)
    @Bean
    public CloseableHttpClient httpClient(PoolingHttpClientConnectionManager poolingConnectionManager) {
        return HttpClientBuilder.create()
            .setConnectionManager(poolingConnectionManager)
            .setDefaultRequestConfig(RequestConfig.custom()
                .setConnectionRequestTimeout(Timeout.ofMilliseconds(hcConfig.getConnectionRequestTimeout()))
                .setConnectionKeepAlive(TimeValue.ofMilliseconds(hcConfig.getKeepAlive()))
                .build())
            .build();
    }
    //
    // @Bean
    // public RestApiService restApiService(RestClient restClient) {
    //     return new RestApiService(restClient);
    // }
}
