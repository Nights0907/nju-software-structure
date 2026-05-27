package edu.assignment.hpsadd;

import java.time.Duration;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "assignment")
public record AssignmentProperties(
        String model,
        String logDir,
        HttpProperties http
) {

    public AssignmentProperties {
        if (http == null) {
            throw new IllegalArgumentException("assignment.http must not be null");
        }
    }

    public record HttpProperties(
            Duration connectTimeout,
            Duration readTimeout
    ) {

        public HttpProperties {
            if (connectTimeout == null || connectTimeout.isZero() || connectTimeout.isNegative()) {
                throw new IllegalArgumentException("assignment.http.connect-timeout must be positive");
            }
            if (readTimeout == null || readTimeout.isZero() || readTimeout.isNegative()) {
                throw new IllegalArgumentException("assignment.http.read-timeout must be positive");
            }
        }
    }
}