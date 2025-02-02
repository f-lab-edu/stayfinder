package com.vacation.platform.stayfinder.login.dto;

import lombok.Data;

@Data
public class LoginDTO {
    private String email;
    private String password;
    private String accessToken;
    private String refreshToken;
}
