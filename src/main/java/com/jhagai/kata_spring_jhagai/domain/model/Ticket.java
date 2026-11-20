package com.jhagai.kata_spring_jhagai.domain.model;

public record Ticket(
        Long id,
        String key,
        String title,
        String description,
        TicketStatus status,
        Long assigneeId,
        Long sprintId) {

    public Ticket withStatus(TicketStatus newStatus) {
        return new Ticket(id, key, title, description, newStatus, assigneeId, sprintId);
    }

    public Ticket withAssigneeId(Long newAssigneeId) {
        return new Ticket(id, key, title, description, status, newAssigneeId, sprintId);
    }

    public Ticket withSprintId(Long newSprintId) {
        return new Ticket(id, key, title, description, status, assigneeId, newSprintId);
    }
}
