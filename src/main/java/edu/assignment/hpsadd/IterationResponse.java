package edu.assignment.hpsadd;

public record IterationResponse(
        String sessionId,
        int iteration,
        String model,
        String promptTemplate,
        long durationMs,
        String tokenUsage,
        String response,
        String logFile
) {
}
