package com.vacation.platform.stayfinder.util;

import com.vacation.platform.stayfinder.login.dto.JwtTokenResponse;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Base64;
import java.util.Date;
import io.jsonwebtoken.security.Keys;
import javax.crypto.SecretKey;

@Component
public class JwtUtil {

    private final JwtParser jwtParser;
    private final SecretKey secretKey;

    public JwtUtil(@Value("${secret.key}") String key) {
        this.secretKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode(key));
        this.jwtParser = Jwts.parser().verifyWith(secretKey).build();
    }


    public String generateAccessToken(String email) {
        return Jwts.builder()
                .subject(email)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 15 ))
                .signWith(secretKey)
                .compact();
    }

    public JwtTokenResponse generateRefreshToken(String email) {
        Instant expiryInstant = Instant.now().plusMillis(1000 * 60 * 60 * 24 * 7);
        LocalDateTime expiryDateTime = LocalDateTime.ofInstant(expiryInstant, ZoneId.systemDefault());

        String token =  Jwts.builder()
                .subject(email)
                .issuedAt(new Date())
                .expiration(Date.from(expiryInstant))
                .signWith(secretKey)
                .compact();

        return new JwtTokenResponse(token, expiryDateTime);
    }

    public boolean validateToken(String token) {
        try {
            jwtParser.parse(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public String getUserEmail(String token) {
        Claims claims = jwtParser.parseSignedClaims(token).getPayload();
        return claims.getSubject();
    }
}
