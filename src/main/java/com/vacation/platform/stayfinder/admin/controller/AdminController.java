package com.vacation.platform.stayfinder.admin.controller;

import com.vacation.platform.stayfinder.admin.dto.AdminRequestDTO;
import com.vacation.platform.stayfinder.admin.service.AdminService;
import com.vacation.platform.stayfinder.common.ErrorType;
import com.vacation.platform.stayfinder.common.StayFinderException;
import com.vacation.platform.stayfinder.corpuser.entity.RequestStatus;
import com.vacation.platform.stayfinder.util.StayFinderResponseDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/api/v1/admin")
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;

    // 사업자 회원 요청 목록 조회
    @GetMapping( "/authority/inquiry")
    public StayFinderResponseDTO<?> authorityInquiry(@Valid @RequestBody AdminRequestDTO.CorpUserRequestInquiryDTO corpUserRequestInquiryDTO) {
        return adminService.authorityInquiry(corpUserRequestInquiryDTO);
    }

    // 사업자 회원 승인, 거절 처리
    @PostMapping("/authority/approved")
    public StayFinderResponseDTO<?> approved(@Valid @RequestBody AdminRequestDTO.CorpUserRequestApprovedDTO corpUserRequestApprovedDTO) {
        RequestStatus requestStatus = RequestStatus.getRequestStatus(corpUserRequestApprovedDTO.getRequestStatus());
        if(requestStatus.equals(RequestStatus.PENDING)){
            throw new StayFinderException(ErrorType.REQUEST_STATUS_IS_NOT_PENDING,
                    Map.of("requestStatus", corpUserRequestApprovedDTO.getRequestStatus()),
                    log::error);
        }

        return adminService.approved(corpUserRequestApprovedDTO);
    }

}
