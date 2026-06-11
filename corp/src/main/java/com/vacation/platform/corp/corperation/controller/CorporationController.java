package com.vacation.platform.corp.corperation.controller;

import com.vacation.platform.api.login.dto.LogOutDTO;
import com.vacation.platform.api.login.dto.LoginDTO;
import com.vacation.platform.api.util.StayFinderResponseDTO;
import com.vacation.platform.corp.corperation.dto.CorpUserDTO;
import com.vacation.platform.corp.corperation.service.CorporationService;
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
public class CorporationController {

    private final CorporationService corporationService;

    @PostMapping("/create")
    public StayFinderResponseDTO<?> createCorpUser(@RequestBody CorpUserDTO corpUserDTO) {
        return corporationService.createCorporationUser(corpUserDTO);
    }

    @PostMapping("/user/login")
    public StayFinderResponseDTO<?> login(@RequestBody LoginDTO loginDTO) {
        return corporationService.login(loginDTO);
    }

    @PostMapping("/user/logout")
    public StayFinderResponseDTO<?> logout(@RequestBody LogOutDTO logOutDTO) {
        return corporationService.logout(logOutDTO);
    }

    @PostMapping("/user/refresh")
    public StayFinderResponseDTO<?> refresh(@RequestBody String refreshToken) {
        return corporationService.refreshToken(refreshToken);
    }

}