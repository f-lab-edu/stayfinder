package com.vacation.platform.stayfinder.util;

import com.vacation.platform.stayfinder.login.dto.JwtTokenResponse;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@Component
public class JwtUtil {

    private final JwtParser jwtParser;
    private final SecretKey secretKey;

    public JwtUtil(@Value("${secret.key}") String key) {
        this.secretKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode(key));
        this.jwtParser = Jwts.parser()
                .verifyWith(secretKey) // JJWT 0.12.x 방식
                .build();
    }

    // 🔹 Access Token 생성 (15분 유효)
    public JwtTokenResponse generateAccessToken(String email) {
        Instant expiryInstant = Instant.now().plusMillis(1000 * 60 * 15);
        LocalDateTime expiryDateTime = LocalDateTime.ofInstant(expiryInstant, ZoneId.systemDefault());

        String accessToke = Jwts.builder()
                .subject(email)
                .issuedAt(new Date())
                .expiration(Date.from(expiryInstant))
                .signWith(secretKey)
                .compact();

         return new JwtTokenResponse(accessToke, expiryDateTime);
    }

    // 🔹 Refresh Token 생성 (7일 유효) + Access Token 포함
    public JwtTokenResponse generateRefreshToken(String email) {
        Instant expiryInstant = Instant.now().plusMillis(1000 * 60 * 60 * 24 * 7);
        LocalDateTime expiryDateTime = LocalDateTime.ofInstant(expiryInstant, ZoneId.systemDefault());

        String refreshToken = Jwts.builder()
                .subject(email)
                .issuedAt(new Date())
                .expiration(Date.from(expiryInstant))
                .signWith(secretKey)
                .compact();

        return new JwtTokenResponse(refreshToken, expiryDateTime);
    }

    // 🔹 토큰 유효성 검증
    public boolean validateToken(String token) {
        try {
            jwtParser.parseSignedClaims(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    // 🔹 토큰에서 이메일(사용자 ID) 추출
    public String getUserEmail(String token) {
        return getClaims(token).getSubject();
    }

    // 🔹 토큰과 사용자 이름 일치 여부 확인
    public boolean validateToken(String token, String username) {
        return (username.equals(getUserEmail(token)) && !isTokenExpired(token));
    }

    // 🔹 JWT 토큰 만료 여부 확인
    public boolean isTokenExpired(String token) {
        return getClaims(token).getExpiration().before(new Date());
    }

    // 🔹 Claims 정보 추출
    private Claims getClaims(String token) {
        return jwtParser.parseSignedClaims(token).getPayload();
    }

}
