package com.vacation.platform.api.login.controller;

import com.vacation.platform.api.login.dto.LogOutDTO;
import com.vacation.platform.api.login.dto.LoginDTO;
import com.vacation.platform.api.login.service.LoginService;
import com.vacation.platform.api.util.StayFinderResponseDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class LoginController {

    private final LoginService loginService;

    @PostMapping("/login")
    public StayFinderResponseDTO<?> login(@Valid @RequestBody LoginDTO loginDTO) {
        return loginService.login(loginDTO);
    }

    @PostMapping("/logout")
    public StayFinderResponseDTO<?> logout(@RequestHeader("Authorization") String token, @Valid @RequestBody LogOutDTO logOutDTO) {
        return loginService.logout(token, logOutDTO);
    }

    @PostMapping("/refresh")
    public StayFinderResponseDTO<?> refresh(@RequestHeader("Authorization") String refreshToken) {
        return loginService.refreshToken(refreshToken);
    }

}
