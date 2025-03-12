package com.vacation.platform.corp.corpuser.controller;

import com.vacation.platform.api.login.dto.LogOutDTO;
import com.vacation.platform.api.login.dto.LoginDTO;
import com.vacation.platform.api.util.StayFinderResponseDTO;
import com.vacation.platform.corp.corpuser.dto.CorpUserDTO;
import com.vacation.platform.corp.corpuser.service.CorpUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/v1/corp")
@RequiredArgsConstructor
public class CorpUserController {

    private final CorpUserService corpUserService;

    @PostMapping("/create")
    public StayFinderResponseDTO<?> createCorpUser(@RequestBody CorpUserDTO corpUserDTO) {
        return corpUserService.createCorpUser(corpUserDTO);
    }

    @PostMapping("/user/login")
    public StayFinderResponseDTO<?> login(@RequestBody LoginDTO loginDTO) {
        return corpUserService.login(loginDTO);
    }

    @PostMapping("/user/logout")
    public StayFinderResponseDTO<?> logout(@RequestBody LogOutDTO logOutDTO) {
        return corpUserService.logout(logOutDTO);
    }

    @PostMapping("/user/refresh")
    public StayFinderResponseDTO<?> refresh(@RequestBody String refreshToken) {
        return corpUserService.refreshToken(refreshToken);
    }

}