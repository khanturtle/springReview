package com.review.springreview.signup.service;

import com.review.springreview.signup.dto.CommentRequestDto;
import com.review.springreview.signup.dto.CommentResponseDto;
import com.review.springreview.signup.dto.PostResponseDto;
import com.review.springreview.signup.entity.Comment;
import com.review.springreview.signup.entity.Post;
import com.review.springreview.signup.repository.CommentRepository;
import com.review.springreview.signup.repository.PostRepository;
import com.review.springreview.signup.security.UserDetailsImpl;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Service
public class CommentService {
    private final CommentRepository commentRepository;
    private final PostRepository postRepository;

    @Transactional
    public CommentResponseDto createComment(Long postId, CommentRequestDto commentRequestDto, UserDetailsImpl userDetails) {
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new IllegalArgumentException("존재하지 않는 게시글 입니다.")
        );
        Comment comment = new Comment(commentRequestDto, userDetails.getUser(), post);
        commentRepository.save(comment);
        return new CommentResponseDto(comment);
    }

    @Transactional
    public CommentResponseDto updateComment(Long commentId, CommentRequestDto commentRequestDto, UserDetailsImpl userDetails) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(
                () -> new IllegalArgumentException("존재하지 않는 댓글 입니다.")
        );
        if (userDetails.getUser().getNickname().equals(comment.getUser().getNickname())) {
            comment.setCommentContent(commentRequestDto.getCommentContent());
            comment.setModifiedAt(LocalDateTime.now());
//            commentRepository.save(comment);
            return new CommentResponseDto(comment);
        } else {
            throw new IllegalArgumentException("댓글 작성자가 아닙니다.");
        }
    }

    @Transactional
    public void deleteComment(Long commentId, UserDetailsImpl userDetails) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(
                () -> new IllegalArgumentException("존재하지 않는 댓글 입니다.")
        );
        if (userDetails.getUser().getNickname().equals(comment.getUser().getNickname())) {
            commentRepository.delete(comment);
        }else{
            throw new IllegalArgumentException("댓글 작성자가 아닙니다.");
        }
    }
}
