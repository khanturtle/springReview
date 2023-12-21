package com.review.springreview.signup.controller;

import com.review.springreview.signup.dto.CommonResponseDto;
import com.review.springreview.signup.dto.PostRequestDto;
import com.review.springreview.signup.dto.PostResponseDto;
import com.review.springreview.signup.entity.Post;
import com.review.springreview.signup.entity.User;
import com.review.springreview.signup.security.UserDetailsImpl;
import com.review.springreview.signup.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.RejectedExecutionException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/posts")
public class PostController {
    private final PostService postService;

    @PostMapping("/create")
    public ResponseEntity<?> createPost(
            @RequestBody PostRequestDto postRequestDto,
            @AuthenticationPrincipal UserDetailsImpl userDetails){
        try {
            PostResponseDto postResponseDto = postService.createPost(postRequestDto,userDetails);
            return ResponseEntity.ok().body(postResponseDto);
        } catch (RejectedExecutionException | IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(new CommonResponseDto(e.getMessage(), HttpStatus.BAD_REQUEST.value()));
        }
    }
    @GetMapping("")
    public List<PostResponseDto> getPosts(){
        return postService.getPosts();
    }

    @GetMapping("/{postId}")
    public PostResponseDto getPost(
            @PathVariable Long postId){
        return postService.getPost(postId);
    }

    @PutMapping("/{postId}")
    public PostResponseDto updatePost(
            @PathVariable Long postId,
            @RequestBody PostRequestDto postRequestDto,
            @AuthenticationPrincipal UserDetailsImpl userDetails){
        return postService.updatePost(postId,postRequestDto,userDetails);
    }
    @DeleteMapping("/{postId}")
    public CommonResponseDto deletePost(
            @PathVariable Long postId,
            @AuthenticationPrincipal UserDetailsImpl userDetails){
        return postService.deletePost(postId,userDetails);
    }
}
