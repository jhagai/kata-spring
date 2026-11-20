package com.jhagai.kata_spring_jhagai.infrastructure.web;

import com.jhagai.kata_spring_jhagai.domain.port.in.CloseSprintUseCase;
import com.jhagai.kata_spring_jhagai.domain.port.in.CreateSprintUseCase;
import com.jhagai.kata_spring_jhagai.domain.port.in.GetSprintStatisticsUseCase;
import com.jhagai.kata_spring_jhagai.domain.port.in.GetSprintUseCase;
import com.jhagai.kata_spring_jhagai.domain.port.in.ListSprintsUseCase;
import com.jhagai.kata_spring_jhagai.infrastructure.web.dto.CloseSprintRequest;
import com.jhagai.kata_spring_jhagai.infrastructure.web.dto.CreateSprintRequest;
import com.jhagai.kata_spring_jhagai.infrastructure.web.dto.SprintCloseResponse;
import com.jhagai.kata_spring_jhagai.infrastructure.web.dto.SprintResponse;
import com.jhagai.kata_spring_jhagai.infrastructure.web.dto.SprintStatisticsResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/sprints")
public class SprintController {

    private final CreateSprintUseCase createSprintUseCase;
    private final GetSprintUseCase getSprintUseCase;
    private final ListSprintsUseCase listSprintsUseCase;
    private final GetSprintStatisticsUseCase getSprintStatisticsUseCase;
    private final CloseSprintUseCase closeSprintUseCase;

    public SprintController(CreateSprintUseCase createSprintUseCase, GetSprintUseCase getSprintUseCase,
                             ListSprintsUseCase listSprintsUseCase,
                             GetSprintStatisticsUseCase getSprintStatisticsUseCase,
                             CloseSprintUseCase closeSprintUseCase) {
        this.createSprintUseCase = createSprintUseCase;
        this.getSprintUseCase = getSprintUseCase;
        this.listSprintsUseCase = listSprintsUseCase;
        this.getSprintStatisticsUseCase = getSprintStatisticsUseCase;
        this.closeSprintUseCase = closeSprintUseCase;
    }

    @PostMapping
    public ResponseEntity<SprintResponse> create(@Valid @RequestBody CreateSprintRequest request) {
        SprintResponse response = SprintResponse.from(
                createSprintUseCase.createSprint(request.name(), request.startDate(), request.endDate()));
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    public SprintResponse getById(@PathVariable Long id) {
        return SprintResponse.from(getSprintUseCase.getById(id));
    }

    @GetMapping
    public List<SprintResponse> listAll() {
        return listSprintsUseCase.listAll().stream().map(SprintResponse::from).toList();
    }

    @GetMapping("/{id}/statistics")
    public SprintStatisticsResponse statistics(@PathVariable Long id) {
        return SprintStatisticsResponse.from(getSprintStatisticsUseCase.getStatistics(id));
    }

    @PostMapping("/{id}/close")
    public SprintCloseResponse close(@PathVariable Long id, @Valid @RequestBody CloseSprintRequest request) {
        return SprintCloseResponse.from(closeSprintUseCase.closeSprint(id, request.nextSprintId()));
    }
}
