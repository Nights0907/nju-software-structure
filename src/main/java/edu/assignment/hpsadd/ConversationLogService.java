package edu.assignment.hpsadd;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import org.springframework.stereotype.Service;

@Service
public class ConversationLogService {

    private static final DateTimeFormatter FILE_TIME = DateTimeFormatter.ofPattern("yyyyMMdd-HHmmss");
    private final AssignmentProperties properties;

    public ConversationLogService(AssignmentProperties properties) {
        this.properties = properties;
    }

    public IterationLogRecord write(
            String sessionId,
            int iteration,
            String promptTemplate,
            String timestamp,
            String model,
            long durationMs,
            int systemPromptChars,
            int userPromptChars,
            int responseChars,
            String tokenUsage,
            String systemPrompt,
            String userPrompt,
            String response
    ) {
        try {
            Path logDir = Path.of(properties.logDir());
            Files.createDirectories(logDir);
            String fileName = "session-" + sessionId + "-iteration-" + iteration + "-" + OffsetDateTime.now().format(FILE_TIME) + ".md";
            Path file = logDir.resolve(fileName);
            String content = "---\n"
                    + "sessionId: " + sessionId + "\n"
                    + "iteration: " + iteration + "\n"
                    + "timestamp: " + timestamp + "\n"
                    + "model: " + model + "\n"
                    + "promptTemplate: " + promptTemplate + "\n"
                    + "durationMs: " + durationMs + "\n"
                    + "systemPromptChars: " + systemPromptChars + "\n"
                    + "userPromptChars: " + userPromptChars + "\n"
                    + "responseChars: " + responseChars + "\n"
                    + "tokenUsage: " + tokenUsage + "\n"
                    + "---\n\n"
                    + "# Conversation Log\n\n"
                    + "## System Prompt\n\n"
                    + systemPrompt + "\n\n"
                    + "## User Prompt\n\n"
                    + userPrompt + "\n\n"
                    + "## Assistant Response\n\n"
                    + response + "\n";
            Files.writeString(file, content, StandardCharsets.UTF_8);
            return new IterationLogRecord(
                    sessionId,
                    iteration,
                    promptTemplate,
                    timestamp,
                    model,
                    durationMs,
                    systemPromptChars,
                    userPromptChars,
                    responseChars,
                    tokenUsage,
                    response,
                    file.toString()
            );
        } catch (IOException e) {
            throw new IllegalStateException("Failed to write conversation log", e);
        }
    }
}
