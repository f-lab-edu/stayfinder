package com.vacation.platform.api.util;

import com.vacation.platform.api.login.dto.JwtTokenResponse;
import com.vacation.platform.api.user.entity.Role;
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
import java.util.Map;

@Component
public class JwtUtil {

    private final JwtParser jwtParser;
    private final SecretKey secretKey;

    public JwtUtil(@Value("${secret.key}") String key) {
        this.secretKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode(key));
        this.jwtParser = Jwts.parser()
                .verifyWith(secretKey)
                .build();
    }

    public JwtTokenResponse generateToken(String email, long expiresInSeconds, Map<String, Role> roles) {

        Instant expiryInstant = Instant.now().plusMillis(expiresInSeconds);
        LocalDateTime expiryDateTime = LocalDateTime.ofInstant(expiryInstant, ZoneId.of("Asia/Seoul"));

        String accessToke = Jwts.builder()
                .subject(email)
                .issuedAt(new Date())
                .expiration(Date.from(expiryInstant))
                .claims(roles)
                .signWith(secretKey)
                .compact();

         return new JwtTokenResponse(accessToke, expiryDateTime);
    }

    public boolean validateToken(String token) {
        try {
            jwtParser.parseSignedClaims(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    public String getUserEmail(String token) {
        return getClaims(token).getSubject();
    }

    public boolean validateToken(String token, String username) {
        return (username.equals(getUserEmail(token)) && !isTokenExpired(token));
    }

    public boolean isTokenExpired(String token) {
        return getClaims(token).getExpiration().before(new Date());
    }

    private Claims getClaims(String token) {
        return jwtParser.parseSignedClaims(token).getPayload();
    }

    public String getUserRole(String token) {
        return getClaims(token).get("role", String.class); // ✅ Role 정보 추출
    }

}
