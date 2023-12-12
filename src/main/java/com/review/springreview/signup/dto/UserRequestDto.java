package com.review.springreview.signup.dto;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class UserRequestDto {

    @Pattern(regexp = "^[A-Za-z0-9]*$",message = "알파벳과 숫자로만 구성되어야 합니다.")
    @Size(min=3,message = "닉네임은 3글자 이상이어야 합니다.")
    private String nickname;

    @Size(min=4,message = "비밀번호는 4글자 이상이어야 합니다.")
    private String password;

    private String passwordCheck;
}
