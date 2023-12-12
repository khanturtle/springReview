package com.review.springreview.signup.controller;

import com.review.springreview.signup.dto.CommonResponseDto;
import com.review.springreview.signup.dto.UserRequestDto;
import com.review.springreview.signup.dto.UserResponseDto;
import com.review.springreview.signup.service.UserService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class UserController {

    private final UserService userService;
    @PostMapping("/signup")
    public ResponseEntity<Object> signup(@Valid @RequestBody UserRequestDto userRequestDto) {
        String msg = userService.signup(userRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED.value())
                .body(new CommonResponseDto(msg, HttpStatus.CREATED.value()));
    }

    @PostMapping("/login")
    public ResponseEntity<Object> login(@Valid @RequestBody UserRequestDto userRequestDto, HttpServletResponse res) {
        String msg = userService.login(userRequestDto,res);
        return ResponseEntity.status(HttpStatus.CREATED.value())
                .body(new CommonResponseDto(msg, HttpStatus.CREATED.value()));
    }
}
