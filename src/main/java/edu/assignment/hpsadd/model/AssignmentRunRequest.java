package edu.assignment.hpsadd.model;

import jakarta.validation.constraints.NotBlank;

public record AssignmentRunRequest(
        @NotBlank String userPrompt
) {
}
