package com.review.springreview.signup.dto;

import jakarta.validation.constraints.Size;
import lombok.Getter;

import java.util.Date;

@Getter
public class PostRequestDto {

    @Size(max=500,message = "제목은 500자 이하여야 합니다.")
    private String title;
    @Size(max=5000,message = "내용은 5000자 이하여야 합니다.")
    private String content;
}
