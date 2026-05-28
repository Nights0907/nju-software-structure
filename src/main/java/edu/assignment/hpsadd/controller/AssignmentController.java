package edu.assignment.hpsadd.controller;

import edu.assignment.hpsadd.model.AssignmentRunRequest;
import edu.assignment.hpsadd.model.AssignmentRunResponse;
import edu.assignment.hpsadd.model.IterationRequest;
import edu.assignment.hpsadd.model.IterationResponse;
import edu.assignment.hpsadd.service.AddIterationService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/assignment")
public class AssignmentController {

    private final AddIterationService addIterationService;

    public AssignmentController(AddIterationService addIterationService) {
        this.addIterationService = addIterationService;
    }

    @GetMapping("/health")
    public String health() {
        return "ok";
    }

    @PostMapping("/iterations")
    public IterationResponse runIteration(@Valid @RequestBody IterationRequest request) {
        return addIterationService.run(request);
    }

    @PostMapping("/runs")
    public AssignmentRunResponse runAssignment(@Valid @RequestBody AssignmentRunRequest request) {
        return addIterationService.runAll(request);
    }
}
