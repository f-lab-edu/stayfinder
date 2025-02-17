package com.vacation.platform.stayfinder.admin.controller;

import com.vacation.platform.stayfinder.admin.dto.AdminRequestDTO;
import com.vacation.platform.stayfinder.admin.service.AdminService;
import com.vacation.platform.stayfinder.util.StayFinderResponseDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/v1/admin")
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;

    @GetMapping("/authority/inquiry")
    public StayFinderResponseDTO<?> authorityInquiry(@RequestBody AdminRequestDTO.CorpUserRequestInquiryDTO corpUserRequestInquiryDTO) {
        return adminService.authorityInquiry(corpUserRequestInquiryDTO);
    }

    @PostMapping("/authority/approved")
    public StayFinderResponseDTO<?> approved(@RequestBody AdminRequestDTO.CorpUserRequestApprovedDTO  corpUserRequestApprovedDTO) {
        return StayFinderResponseDTO.success();
    }

}
