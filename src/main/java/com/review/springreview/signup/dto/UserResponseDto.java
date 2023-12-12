package com.review.springreview.signup.dto;

public class UserResponseDto {
    private String nickname;
    private String password;
    public UserResponseDto(UserRequestDto userRequestDto) {
        this.nickname=userRequestDto.getNickname();
        this.password=userRequestDto.getPassword();
    }
}
