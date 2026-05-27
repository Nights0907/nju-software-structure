package edu.assignment.hpsadd;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public record IterationRequest(
        @Min(1) @Max(4) int iteration,
        @NotBlank String userPrompt
) {
}
