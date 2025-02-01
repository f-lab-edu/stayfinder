package com.vacation.platform.stayfinder.user.dto;

import lombok.Data;

@Data
public class LoginDTO {
    private String email;
    private String password;
    private String accessToken;
    private String refreshToken;
}
