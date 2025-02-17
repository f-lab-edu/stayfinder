package com.vacation.platform.stayfinder.admin.service.impl;

import com.vacation.platform.stayfinder.admin.dto.AdminRequestDTO;
import com.vacation.platform.stayfinder.admin.service.AdminService;
import com.vacation.platform.stayfinder.common.ErrorType;
import com.vacation.platform.stayfinder.common.StayFinderException;
import com.vacation.platform.stayfinder.corpuser.entity.CorpUserRequest;
import com.vacation.platform.stayfinder.corpuser.entity.RequestStatus;
import com.vacation.platform.stayfinder.corpuser.repository.CorpUserRequestRepository;
import com.vacation.platform.stayfinder.util.DateTimeUtil;
import com.vacation.platform.stayfinder.util.StayFinderResponseDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {

    private final CorpUserRequestRepository corpUserRequestRepository;


    @Override
    public StayFinderResponseDTO<?> authorityInquiry(AdminRequestDTO.CorpUserRequestInquiryDTO corpUserRequestInquiryDTO) {

        LocalDateTime startDate = corpUserRequestInquiryDTO.getStartDate() != null ?
                DateTimeUtil.parseStartDate(corpUserRequestInquiryDTO.getStartDate()) : LocalDateTime.now();

        LocalDateTime endDate = corpUserRequestInquiryDTO.getEndDate() != null ?
                DateTimeUtil.parseEndDate(corpUserRequestInquiryDTO.getEndDate()) : LocalDateTime.now();

        if(startDate.isAfter(endDate)) {
            throw new StayFinderException(ErrorType.INVALID_DATE_RANGE,
                    Map.of("corpUserRequestInquiryDTO", corpUserRequestInquiryDTO),
                    log::error);
        }

        List<CorpUserRequest> resultList
                = corpUserRequestRepository.findByCorpUserRequests(startDate, endDate,
                corpUserRequestInquiryDTO.getRequestStatus() == null ? RequestStatus.PENDING : RequestStatus.getRequestStatus(corpUserRequestInquiryDTO.getRequestStatus()));


        return StayFinderResponseDTO.success(resultList);
    }

    @Override
    public StayFinderResponseDTO<?> approved(AdminRequestDTO.CorpUserRequestApprovedDTO corpUserRequestApprovedDTO) {
        return StayFinderResponseDTO.success();
    }


}
