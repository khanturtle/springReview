package com.review.springreview.signup.controller;

import com.review.springreview.signup.dto.CommentRequestDto;
import com.review.springreview.signup.dto.CommentResponseDto;
import com.review.springreview.signup.dto.CommonResponseDto;
import com.review.springreview.signup.dto.PostResponseDto;
import com.review.springreview.signup.repository.CommentRepository;
import com.review.springreview.signup.security.UserDetailsImpl;
import com.review.springreview.signup.service.CommentService;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.RejectedExecutionException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/comments")
public class CommentController {
    private final CommentService commentService;

    @PostMapping("/{postId}")
    public ResponseEntity<?> createComment(
            @PathVariable Long postId,
            @RequestBody CommentRequestDto commentRequestDto,
            @AuthenticationPrincipal UserDetailsImpl userDetails){
        try {
            CommentResponseDto commentResponseDto = commentService.createComment(postId,commentRequestDto,userDetails);
            return ResponseEntity.ok().body(commentResponseDto);
        } catch (RejectedExecutionException | IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(new CommonResponseDto(e.getMessage(), HttpStatus.BAD_REQUEST.value()));
        }
    }

    @PatchMapping("/{commentId}")
    public ResponseEntity<?> updateComment(
            @PathVariable Long commentId,
            @RequestBody CommentRequestDto commentRequestDto,
            @AuthenticationPrincipal UserDetailsImpl userDetails){
        try {
            CommentResponseDto commentResponseDto = commentService.updateComment(commentId,commentRequestDto,userDetails);
            return ResponseEntity.ok().body(commentResponseDto);
        } catch (RejectedExecutionException | IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(new CommonResponseDto(e.getMessage(), HttpStatus.BAD_REQUEST.value()));
        }
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<?> deleteComment(
            @PathVariable Long commentId,
            @AuthenticationPrincipal UserDetailsImpl userDetails){
        try {
            commentService.deleteComment(commentId,userDetails);
            return ResponseEntity.ok().body("댓글이 삭제 되었습니다.");
        } catch (RejectedExecutionException | IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(new CommonResponseDto(e.getMessage(), HttpStatus.BAD_REQUEST.value()));
        }
    }
}
