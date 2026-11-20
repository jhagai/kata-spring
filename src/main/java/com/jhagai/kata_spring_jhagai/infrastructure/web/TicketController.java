package com.jhagai.kata_spring_jhagai.infrastructure.web;

import com.jhagai.kata_spring_jhagai.domain.port.in.AddCommentToTicketUseCase;
import com.jhagai.kata_spring_jhagai.domain.port.in.AddTagToTicketUseCase;
import com.jhagai.kata_spring_jhagai.domain.port.in.AssignTicketUseCase;
import com.jhagai.kata_spring_jhagai.domain.port.in.CreateTicketUseCase;
import com.jhagai.kata_spring_jhagai.domain.port.in.GetTicketDetailUseCase;
import com.jhagai.kata_spring_jhagai.domain.port.in.GetTicketFullUseCase;
import com.jhagai.kata_spring_jhagai.domain.port.in.ListTicketsForSprintUseCase;
import com.jhagai.kata_spring_jhagai.domain.port.in.MoveTicketToSprintUseCase;
import com.jhagai.kata_spring_jhagai.infrastructure.web.dto.AddCommentRequest;
import com.jhagai.kata_spring_jhagai.infrastructure.web.dto.AddTagRequest;
import com.jhagai.kata_spring_jhagai.infrastructure.web.dto.AssignTicketRequest;
import com.jhagai.kata_spring_jhagai.infrastructure.web.dto.CommentResponse;
import com.jhagai.kata_spring_jhagai.infrastructure.web.dto.CreateTicketRequest;
import com.jhagai.kata_spring_jhagai.infrastructure.web.dto.MoveTicketRequest;
import com.jhagai.kata_spring_jhagai.infrastructure.web.dto.TicketDetailResponse;
import com.jhagai.kata_spring_jhagai.infrastructure.web.dto.TicketFullResponse;
import com.jhagai.kata_spring_jhagai.infrastructure.web.dto.TicketResponse;
import com.jhagai.kata_spring_jhagai.infrastructure.web.dto.TicketSummaryResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/tickets")
public class TicketController {

    private final CreateTicketUseCase createTicketUseCase;
    private final GetTicketDetailUseCase getTicketDetailUseCase;
    private final GetTicketFullUseCase getTicketFullUseCase;
    private final ListTicketsForSprintUseCase listTicketsForSprintUseCase;
    private final AddCommentToTicketUseCase addCommentToTicketUseCase;
    private final AddTagToTicketUseCase addTagToTicketUseCase;
    private final AssignTicketUseCase assignTicketUseCase;
    private final MoveTicketToSprintUseCase moveTicketToSprintUseCase;

    public TicketController(CreateTicketUseCase createTicketUseCase,
                             GetTicketDetailUseCase getTicketDetailUseCase,
                             GetTicketFullUseCase getTicketFullUseCase,
                             ListTicketsForSprintUseCase listTicketsForSprintUseCase,
                             AddCommentToTicketUseCase addCommentToTicketUseCase,
                             AddTagToTicketUseCase addTagToTicketUseCase,
                             AssignTicketUseCase assignTicketUseCase,
                             MoveTicketToSprintUseCase moveTicketToSprintUseCase) {
        this.createTicketUseCase = createTicketUseCase;
        this.getTicketDetailUseCase = getTicketDetailUseCase;
        this.getTicketFullUseCase = getTicketFullUseCase;
        this.listTicketsForSprintUseCase = listTicketsForSprintUseCase;
        this.addCommentToTicketUseCase = addCommentToTicketUseCase;
        this.addTagToTicketUseCase = addTagToTicketUseCase;
        this.assignTicketUseCase = assignTicketUseCase;
        this.moveTicketToSprintUseCase = moveTicketToSprintUseCase;
    }

    @PostMapping
    public ResponseEntity<TicketResponse> create(@Valid @RequestBody CreateTicketRequest request) {
        TicketResponse response = TicketResponse.from(
                createTicketUseCase.createTicket(request.sprintId(), request.title(), request.description()));
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    public TicketDetailResponse getById(@PathVariable Long id) {
        return TicketDetailResponse.from(getTicketDetailUseCase.getById(id));
    }

    @GetMapping("/{id}/full")
    public TicketFullResponse getFull(@PathVariable Long id) {
        return TicketFullResponse.from(getTicketFullUseCase.getFull(id));
    }

    @GetMapping
    public List<TicketSummaryResponse> listForSprint(@RequestParam Long sprintId) {
        return listTicketsForSprintUseCase.listForSprint(sprintId).stream().map(TicketSummaryResponse::from).toList();
    }

    @PostMapping("/{id}/comments")
    public ResponseEntity<CommentResponse> addComment(@PathVariable Long id, @Valid @RequestBody AddCommentRequest request) {
        CommentResponse response = CommentResponse.from(
                addCommentToTicketUseCase.addComment(id, request.authorId(), request.body()));
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/{id}/tags")
    public TicketResponse addTag(@PathVariable Long id, @Valid @RequestBody AddTagRequest request) {
        return TicketResponse.from(addTagToTicketUseCase.addTag(id, request.name()));
    }

    @PostMapping("/{id}/assign")
    public TicketResponse assign(@PathVariable Long id, @Valid @RequestBody AssignTicketRequest request) {
        return TicketResponse.from(assignTicketUseCase.assign(id, request.userId()));
    }

    @PostMapping("/{id}/move")
    public TicketResponse move(@PathVariable Long id, @Valid @RequestBody MoveTicketRequest request) {
        return TicketResponse.from(moveTicketToSprintUseCase.moveToSprint(id, request.sprintId()));
    }
}
