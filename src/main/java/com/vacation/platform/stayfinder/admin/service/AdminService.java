package com.vacation.platform.stayfinder.admin.service;

import com.vacation.platform.stayfinder.admin.dto.AdminRequestDTO;
import com.vacation.platform.stayfinder.util.StayFinderResponseDTO;
import org.springframework.stereotype.Service;

@Service
public interface AdminService {

    StayFinderResponseDTO<?> authorityInquiry(AdminRequestDTO.CorpUserRequestInquiryDTO corpUserRequestInquiryDTO);

    StayFinderResponseDTO<?> approved(AdminRequestDTO.CorpUserRequestApprovedDTO corpUserRequestApprovedDTO);

}
