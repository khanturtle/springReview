package com.review.springreview.signup.service;

import com.review.springreview.signup.dto.UserRequestDto;
import com.review.springreview.signup.entity.User;
import com.review.springreview.signup.jwt.JwtUtil;
import com.review.springreview.signup.repository.UserRepository;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;

    public String signup(UserRequestDto userRequestDto) {
        User user;
        if (!(userRequestDto.getPassword().equals(userRequestDto.getPasswordCheck()))) {
            return "비밀번호가 일치하지 않습니다.";
        } else if (userRequestDto.getPassword().contains(userRequestDto.getNickname())) {
            return "비밀번호에 닉네임이 포함됩니다.";
        } else if (userRepository.findByNickname(userRequestDto.getNickname()).isPresent()) {
            return "중복된 닉네임 입니다.";
        } else {
            user = new User(userRequestDto.getNickname(), userRequestDto.getPassword());
        }
        userRepository.save(user);
        return "회원가입 성공";
    }

    public String login(UserRequestDto userRequestDto, HttpServletResponse res) {
        User user = userRepository.findByNickname(userRequestDto.getNickname()).orElse(null);
        if(user==null){
            return "닉네임 또는 패스워드를 확인해주세요.";
        }
        if (user.getNickname().equals(userRequestDto.getNickname()) && user.getPassword().equals(userRequestDto.getPassword())) {
            String token = jwtUtil.createToken(userRequestDto.getNickname(), user.getRole());
            jwtUtil.addJwtToCookie(token, res);
            return "로그인 성공";
        } else {
            return "닉네임 또는 패스워드를 확인해주세요.";
        }
    }
}
