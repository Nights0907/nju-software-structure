package edu.assignment.hpsadd.infrastructure.prompt;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;

@Component
public class PromptTemplateLoader {

    private final ResourceLoader resourceLoader;

    public PromptTemplateLoader(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    public String load(String location) {
        try {
            Resource resource = resourceLoader.getResource(location);
            return new String(resource.getInputStream().readAllBytes(), StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new IllegalStateException("Failed to load prompt template: " + location, e);
        }
    }
}
