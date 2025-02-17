package com.vacation.platform.stayfinder.admin.controller;

import com.vacation.platform.stayfinder.util.StayFinderResponseDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/v1/admin")
@RequiredArgsConstructor
public class AdminController {

    @GetMapping("/authority/inquiry")
    public StayFinderResponseDTO<?> authorityInquiry(){

        return StayFinderResponseDTO.success();
    }

}
