package com.vacation.platform.stayfinder.login.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class JwtTokenResponse {
    private String Token;
    private LocalDateTime TokenExpiryDate;
}
