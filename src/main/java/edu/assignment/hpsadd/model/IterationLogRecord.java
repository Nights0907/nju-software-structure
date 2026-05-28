package edu.assignment.hpsadd.model;

public record IterationLogRecord(
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
        String response,
        String logFile
) {
}
