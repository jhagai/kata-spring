package com.jhagai.kata_spring_jhagai.infrastructure.persistence.entity;

import com.jhagai.kata_spring_jhagai.domain.model.TicketStatus;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OrderBy;
import jakarta.persistence.Table;

import java.util.ArrayList;
import java.util.List;

/**
 * Fetch strategy here is deliberate: nothing is EAGER. {@code assignee} and
 * {@code sprint} are forced to LAZY (JPA's default for @ManyToOne is EAGER) so that
 * reading them outside the right transaction boundary is a real trap (EXERCISE A),
 * not an accident of the ORM default. {@code comments} and {@code tags} are both
 * List-typed collections on this same entity, which means a single JPQL query doing
 * {@code JOIN FETCH} on both throws MultipleBagFetchException (EXERCISE C) — do not
 * "fix" that by converting either of them to a Set.
 */
@Entity
@Table(name = "tickets")
public class TicketJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String key;

    @Column(nullable = false)
    private String title;

    @Column(length = 2000)
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TicketStatus status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "assignee_id")
    private UserJpaEntity assignee;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sprint_id")
    private SprintJpaEntity sprint;

    @OneToMany(mappedBy = "ticket", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("createdAt ASC")
    private List<CommentJpaEntity> comments = new ArrayList<>();

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "ticket_tag",
            joinColumns = @JoinColumn(name = "ticket_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id"))
    private List<TagJpaEntity> tags = new ArrayList<>();

    protected TicketJpaEntity() {
    }

    public TicketJpaEntity(String key, String title, String description, TicketStatus status,
                            UserJpaEntity assignee, SprintJpaEntity sprint) {
        this.key = key;
        this.title = title;
        this.description = description;
        this.status = status;
        this.assignee = assignee;
        this.sprint = sprint;
    }

    public Long getId() {
        return id;
    }

    public String getKey() {
        return key;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public TicketStatus getStatus() {
        return status;
    }

    public void setStatus(TicketStatus status) {
        this.status = status;
    }

    public UserJpaEntity getAssignee() {
        return assignee;
    }

    public void setAssignee(UserJpaEntity assignee) {
        this.assignee = assignee;
    }

    public SprintJpaEntity getSprint() {
        return sprint;
    }

    public void setSprint(SprintJpaEntity sprint) {
        this.sprint = sprint;
    }

    public List<CommentJpaEntity> getComments() {
        return comments;
    }

    public List<TagJpaEntity> getTags() {
        return tags;
    }
}
