package com.review.springreview.signup.dto;

import com.review.springreview.signup.entity.Comment;
import com.review.springreview.signup.entity.Post;
import com.review.springreview.signup.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

@Getter
public class CommentResponseDto {
    private String commentContent;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    public CommentResponseDto(Comment comment) {
        this.commentContent = comment.getCommentContent();
        this.createdAt = comment.getCreatedAt();
        this.modifiedAt = comment.getModifiedAt();
    }
}
