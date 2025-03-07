package com.vacation.platform.stayfinder.corpuser.controller;

import com.vacation.platform.stayfinder.corpuser.dto.CorpUserDTO;
import com.vacation.platform.stayfinder.corpuser.service.CorpUserService;
import com.vacation.platform.stayfinder.util.StayFinderResponseDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/v1/corp/user")
@RequiredArgsConstructor
public class CorpUserController {

    private final CorpUserService corpUserService;

    @PostMapping("/create")
    public StayFinderResponseDTO<?> createCorpUser(@RequestBody CorpUserDTO CorpUserDTO) {
        return corpUserService.createCorpUser(CorpUserDTO);
    }

    @PostMapping("/login")
    public StayFinderResponseDTO<?> login(@RequestBody CorpUserDTO CorpUserDTO) {
        return corpUserService.login(CorpUserDTO);
    }

}