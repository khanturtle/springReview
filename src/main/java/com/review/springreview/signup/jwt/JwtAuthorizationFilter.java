package com.review.springreview.signup.jwt;

import com.review.springreview.signup.security.UserDetailsService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.review.springreview.signup.dto.CommonResponseDto;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Objects;

@Slf4j(topic = "JWT 검증 및 인가")
@RequiredArgsConstructor
public class JwtAuthorizationFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final UserDetailsService userDetailsService;
    private final ObjectMapper objectMapper;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = jwtUtil.getTokenFromRequest(request);
        if (Objects.nonNull(token)) {
            if (jwtUtil.validateToken(token)) {
                Claims info = jwtUtil.getUserInfoFromToken(token);
                String nickname = info.getSubject();
                SecurityContext context = SecurityContextHolder.createEmptyContext();
                UserDetails userDetails = userDetailsService.getUserDetails(nickname);
                Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                context.setAuthentication(authentication);
                SecurityContextHolder.setContext(context);
            } else {
                // 인증정보가 존재하지 않을때
                CommonResponseDto commonResponseDto = new CommonResponseDto("토큰이 유효하지 않습니다.", HttpStatus.BAD_REQUEST.value());
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                response.setContentType("application/json; charset=UTF-8");
                response.getWriter().write(objectMapper.writeValueAsString(commonResponseDto));
                return;
            }
        }
        filterChain.doFilter(request, response);
    }
}
