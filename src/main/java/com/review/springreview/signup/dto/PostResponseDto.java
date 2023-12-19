package com.review.springreview.signup.dto;

import com.review.springreview.signup.entity.Post;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;

@Getter
@NoArgsConstructor
public class PostResponseDto {
    private String title;
    private String content;
    private String nickname;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    public PostResponseDto(PostRequestDto postRequestDto) {
        this.title = postRequestDto.getTitle();
        this.content = postRequestDto.getContent();
    }

    public PostResponseDto(Post post) {
        this.title = post.getTitle();
        this.content = post.getContent();
        this.nickname = post.getUser().getNickname();
        this.createdAt = post.getCreatedAt();
        this.modifiedAt = post.getModifiedAt();
    }
}
