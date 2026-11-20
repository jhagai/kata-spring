package com.jhagai.kata_spring_jhagai.infrastructure.persistence;

import com.jhagai.kata_spring_jhagai.domain.model.Comment;
import com.jhagai.kata_spring_jhagai.domain.port.out.CommentRepository;
import com.jhagai.kata_spring_jhagai.infrastructure.persistence.entity.CommentJpaEntity;
import com.jhagai.kata_spring_jhagai.infrastructure.persistence.entity.TicketJpaEntity;
import com.jhagai.kata_spring_jhagai.infrastructure.persistence.entity.UserJpaEntity;
import com.jhagai.kata_spring_jhagai.infrastructure.persistence.mapper.CommentMapper;
import com.jhagai.kata_spring_jhagai.infrastructure.persistence.repository.CommentJpaRepository;
import com.jhagai.kata_spring_jhagai.infrastructure.persistence.repository.TicketJpaRepository;
import com.jhagai.kata_spring_jhagai.infrastructure.persistence.repository.UserJpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CommentRepositoryAdapter implements CommentRepository {

    private final CommentJpaRepository commentJpaRepository;
    private final TicketJpaRepository ticketJpaRepository;
    private final UserJpaRepository userJpaRepository;

    public CommentRepositoryAdapter(CommentJpaRepository commentJpaRepository,
                                     TicketJpaRepository ticketJpaRepository,
                                     UserJpaRepository userJpaRepository) {
        this.commentJpaRepository = commentJpaRepository;
        this.ticketJpaRepository = ticketJpaRepository;
        this.userJpaRepository = userJpaRepository;
    }

    @Override
    public Comment save(Comment comment) {
        TicketJpaEntity ticketRef = ticketJpaRepository.getReferenceById(comment.ticketId());
        UserJpaEntity authorRef = userJpaRepository.getReferenceById(comment.authorId());
        CommentJpaEntity entity = new CommentJpaEntity(ticketRef, authorRef, comment.body(), comment.createdAt());
        return CommentMapper.toDomain(commentJpaRepository.save(entity));
    }

    @Override
    public List<Comment> findByTicketId(Long ticketId) {
        return commentJpaRepository.findByTicketIdOrderByCreatedAtAsc(ticketId).stream()
                .map(CommentMapper::toDomain)
                .toList();
    }

    @Override
    public long countByTicketId(Long ticketId) {
        return commentJpaRepository.countByTicketId(ticketId);
    }
}
