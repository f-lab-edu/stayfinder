package com.vacation.platform.stayfinder.common.jwt.filter;

import com.vacation.platform.stayfinder.login.service.RefreshTokenRedisService;
import com.vacation.platform.stayfinder.util.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

@Slf4j
@Component
public class LoginFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;

    private final RefreshTokenRedisService refreshTokenRedisService;

    private static final List<String> FILTERED_URLS = List.of("/api/v1/certify/**", "/api/v1/terms/**",
            "/api/v1/users/**", "/api/v1/user/login");

    public LoginFilter(JwtUtil jwtUtil, RefreshTokenRedisService refreshTokenRedisService) {
        this.jwtUtil = jwtUtil;
        this.refreshTokenRedisService  = refreshTokenRedisService;
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
       return FILTERED_URLS.stream().anyMatch(request.getServletPath()::startsWith);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, @NotNull HttpServletResponse response,
                                    @NotNull FilterChain filterChain) throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization");

        if(authHeader == null || !authHeader.startsWith("Bearer ")) {
            log.error("Token이 존재하지 않습니다.");
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Token이 존재하지 않습니다.");
            return;
        }

        String token = Objects.requireNonNull(authHeader).substring(7);
        if(!jwtUtil.validateToken(token) || refreshTokenRedisService.getToken(token).isEmpty()) {
            log.error("Access Token이 유효하지 않습니다.");
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Access Token이 유효하지 않습니다.");
            return;
        }

        filterChain.doFilter(request, response);
    }
}