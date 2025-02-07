package com.vacation.platform.stayfinder.login.controller;

import com.vacation.platform.stayfinder.login.dto.LogOutDTO;
import com.vacation.platform.stayfinder.login.dto.LoginDTO;
import com.vacation.platform.stayfinder.login.service.LoginService;
import com.vacation.platform.stayfinder.util.StayFinderResponseDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class LoginController {

    private final LoginService loginService;

    @PostMapping("/login")
    public ResponseEntity<StayFinderResponseDTO<?>> login(@Valid @RequestBody LoginDTO loginDTO) {
        return loginService.login(loginDTO);
    }

    @PostMapping("/logout")
    public ResponseEntity<StayFinderResponseDTO<?>> logout(@RequestHeader("Authorization")  String token, @Valid @RequestBody LogOutDTO logOutDTO) {
        return loginService.logout(token, logOutDTO);
    }

    @PostMapping("/refresh")
    public ResponseEntity<StayFinderResponseDTO<?>> refresh(/*@Valid @RequestBody LoginDTO loginDTO*/) {
        return null;
    }

}
