package com.vacation.platform.stayfinder.user.controller;

import com.vacation.platform.stayfinder.user.dto.LoginDTO;
import com.vacation.platform.stayfinder.user.service.serviceImpl.UserServiceImpl;
import com.vacation.platform.stayfinder.util.StayFinderResponseDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class LoginController {

    private final UserServiceImpl userService;

    @GetMapping("/login")
    public ResponseEntity<StayFinderResponseDTO<?>> login(@Valid @RequestBody LoginDTO loginDTO) {
        return userService.login(loginDTO);
    }

}
