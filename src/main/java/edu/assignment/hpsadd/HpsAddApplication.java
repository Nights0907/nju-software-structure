package edu.assignment.hpsadd;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@ConfigurationPropertiesScan
public class HpsAddApplication {

    public static void main(String[] args) {
        SpringApplication.run(HpsAddApplication.class, args);
    }

    @Bean
    public Runnable validateDashScopeApiKey(@Value("${spring.ai.dashscope.api-key:}") String apiKey) {
        if (apiKey == null || apiKey.isBlank()) {
            throw new IllegalStateException("spring.ai.dashscope.api-key must be set before starting the application.");
        }
        return () -> {
        };
    }
}
