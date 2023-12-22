package com.review.springreview.signup.entity;

import com.review.springreview.signup.dto.CommentRequestDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@RequiredArgsConstructor
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String commentContent;
    @Column(name = "createdAt", updatable = false)
    @CreatedDate
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime createdAt;

    @Column(name = "modifiedAt")
    @LastModifiedDate
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime modifiedAt;
    @ManyToOne
    @JoinColumn(name = "userId")
    private User user;

    @ManyToOne
    @JoinColumn(name = "postId")
    private Post post;

    public Comment(CommentRequestDto commentRequestDto, User user, Post post) {
        this.commentContent = commentRequestDto.getCommentContent();
        this.user = user;
        this.post = post;
        this.createdAt = LocalDateTime.now();
        this.modifiedAt = LocalDateTime.now();
    }

    public Comment(CommentRequestDto commentRequestDto) {
        this.commentContent = commentRequestDto.getCommentContent();
        this.modifiedAt = LocalDateTime.now();
    }
}
