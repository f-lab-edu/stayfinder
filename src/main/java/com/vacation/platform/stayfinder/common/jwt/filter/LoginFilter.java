package com.vacation.platform.stayfinder.common.jwt.filter;

import com.vacation.platform.stayfinder.login.service.TokenRedisService;
import com.vacation.platform.stayfinder.util.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

@Slf4j
@Component
public class LoginFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;

    private final TokenRedisService tokenRedisService;

    private static final List<String> FILTERED_URLS = List.of("/api/v1/certify", "/api/v1/terms",
            "/api/v1/users", "/api/v1/user/login", "/api/v1/corp/request/approval", "/api/v1/search");

    public LoginFilter(JwtUtil jwtUtil, TokenRedisService tokenRedisService) {
        this.jwtUtil = jwtUtil;
        this.tokenRedisService = tokenRedisService;
    }

    @Override
    protected boolean shouldNotFilter(@NotNull HttpServletRequest request) {
        return FILTERED_URLS.stream()
                .anyMatch(url -> request.getServletPath().startsWith(url));
    }

    @Override
    protected void doFilterInternal(@NotNull HttpServletRequest request, @NotNull HttpServletResponse response,
                                    @NotNull FilterChain filterChain) throws ServletException, IOException {

        if(FILTERED_URLS.stream().noneMatch(url -> request.getServletPath().startsWith(url))) {

            String authHeader = request.getHeader("Authorization");

            if (authHeader == null) {
                log.error("Token이 존재하지 않습니다.");
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Token이 존재하지 않습니다.");
                return;
            }

            String email = jwtUtil.getUserEmail(authHeader);
            String role = jwtUtil.getUserRole(authHeader);

            if (!jwtUtil.validateToken(authHeader)) {
                log.error("Access Token이 유효하지 않습니다.");
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Access Token이 유효하지 않습니다.");
                return;
            }

            if(!request.getServletPath().equals("/api/v1/user/refresh")) {
                if(tokenRedisService.getToken(authHeader).isEmpty()) {
                    log.error("Access Token이 존재하지 않습니다.");
                    response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Access Token이 존재하지 않습니다.");
                    return;
                }
            }

            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                    email, null, Collections.singleton(new SimpleGrantedAuthority(role))
            );

            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        filterChain.doFilter(request, response);
    }
}