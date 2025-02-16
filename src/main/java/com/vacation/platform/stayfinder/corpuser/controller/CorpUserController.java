package com.vacation.platform.stayfinder.corpuser.controller;

import com.vacation.platform.stayfinder.util.StayFinderResponseDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController("/api/v1/corp/user")
@RequiredArgsConstructor
public class CorpUserController {

    @PostMapping("/creat")
    public StayFinderResponseDTO<?> creatCorpUser() {

        return StayFinderResponseDTO.success();
    }

}
