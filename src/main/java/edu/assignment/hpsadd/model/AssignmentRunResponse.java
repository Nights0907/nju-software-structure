package edu.assignment.hpsadd.model;

import java.util.List;

public record AssignmentRunResponse(
        String sessionId,
        String model,
        int totalIterations,
        List<IterationResponse> iterations
) {
}
