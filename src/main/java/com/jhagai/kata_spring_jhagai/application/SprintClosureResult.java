package com.jhagai.kata_spring_jhagai.application;

import com.jhagai.kata_spring_jhagai.domain.model.Sprint;
import com.jhagai.kata_spring_jhagai.domain.model.Ticket;

import java.util.List;

public record SprintClosureResult(Sprint closedSprint, List<Ticket> ticketsToMove) {
}
