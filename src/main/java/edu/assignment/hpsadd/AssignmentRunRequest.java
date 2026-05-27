package edu.assignment.hpsadd;

import jakarta.validation.constraints.NotBlank;

public record AssignmentRunRequest(
        @NotBlank String userPrompt
) {
}
