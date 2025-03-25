package com.vacation.platform.corp.admin.service;

import com.vacation.platform.api.util.StayFinderResponseDTO;
import com.vacation.platform.corp.admin.dto.AdminRequestDTO;
import org.springframework.stereotype.Service;

@Service
public interface AdminService {
    StayFinderResponseDTO<?> authorityInquiry(AdminRequestDTO.CorporationRequestInquiryDTO corporationRequestInquiryDTO);
    StayFinderResponseDTO<?> approved(AdminRequestDTO.CorporationRequestApprovedDTO corpUserRequestApprovedDTO);
}
