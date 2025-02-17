package com.vacation.platform.stayfinder.corpuser.controller;

import com.vacation.platform.stayfinder.corpuser.dto.CorpUserRequestDTO;
import com.vacation.platform.stayfinder.corpuser.service.CorpUserRequestService;
import com.vacation.platform.stayfinder.util.StayFinderResponseDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController("/api/v1/corp/request")
@RequiredArgsConstructor
public class CorpUserRequestController {

    private final CorpUserRequestService corpUserRequestService;

    @PostMapping("/approval")
    public StayFinderResponseDTO<?> approvalRequest(@RequestBody CorpUserRequestDTO corpUserRequestDTO) {
        return corpUserRequestService.approvalRequest(corpUserRequestDTO);
    }

}
