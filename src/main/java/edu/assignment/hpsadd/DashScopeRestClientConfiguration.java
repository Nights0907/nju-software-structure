package edu.assignment.hpsadd;

import org.springframework.boot.web.client.RestClientCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.SimpleClientHttpRequestFactory;

@Configuration(proxyBeanMethods = false)
public class DashScopeRestClientConfiguration {

    @Bean
    RestClientCustomizer dashScopeRestClientCustomizer(AssignmentProperties properties) {
        return restClientBuilder -> {
            SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
            requestFactory.setConnectTimeout(Math.toIntExact(properties.http().connectTimeout().toMillis()));
            requestFactory.setReadTimeout(Math.toIntExact(properties.http().readTimeout().toMillis()));
            restClientBuilder.requestFactory(requestFactory);
        };
    }
}