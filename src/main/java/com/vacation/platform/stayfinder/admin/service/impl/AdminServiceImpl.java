package com.vacation.platform.stayfinder.admin.service.impl;

import com.vacation.platform.stayfinder.admin.dto.AdminRequestDTO;
import com.vacation.platform.stayfinder.admin.service.AdminService;
import com.vacation.platform.stayfinder.common.ErrorType;
import com.vacation.platform.stayfinder.common.StayFinderException;
import com.vacation.platform.stayfinder.corpuser.entity.CorpStatus;
import com.vacation.platform.stayfinder.corpuser.entity.CorpUser;
import com.vacation.platform.stayfinder.corpuser.entity.CorpUserRequest;
import com.vacation.platform.stayfinder.corpuser.entity.RequestStatus;
import com.vacation.platform.stayfinder.corpuser.repository.CorpUserRepository;
import com.vacation.platform.stayfinder.corpuser.repository.CorpUserRequestRepository;
import com.vacation.platform.stayfinder.util.DateTimeUtil;
import com.vacation.platform.stayfinder.util.StayFinderResponseDTO;
import jakarta.transaction.Transactional;
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

    private final CorpUserRepository corpUserRepository;


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

        if(resultList.isEmpty()) {
            return StayFinderResponseDTO.success("데이터가 존재하지 않습니다.");
        }

        return StayFinderResponseDTO.success(resultList);
    }

    @Override
    @Transactional
    public StayFinderResponseDTO<?> approved(AdminRequestDTO.CorpUserRequestApprovedDTO corpUserRequestApprovedDTO) {
        CorpUserRequest corpUserRequest = corpUserRequestRepository.findById(corpUserRequestApprovedDTO.getCorpUserId())
                .orElseThrow( () -> new StayFinderException(ErrorType.BUSINESS_LICENSE_IS_EMPTY,
                        Map.of("businessLicenseId", corpUserRequestApprovedDTO.getCorpUserId()),
                        log::error));

        RequestStatus requestStatus = RequestStatus.getRequestStatus(corpUserRequestApprovedDTO.getRequestStatus());

        corpUserRequest.setStatus(requestStatus);
        corpUserRequest.setApprovedAt(LocalDateTime.now());
        if(requestStatus.compareTo(RequestStatus.APPROVED) == 0) {
            try {
                saveCorpUser(corpUserRequest);
            } catch (Exception e) {
                throw new StayFinderException(ErrorType.DB_ERROR,
                        Map.of("corpUser", corpUserRequest),
                        log::error,
                        e);
            }
        }

        return StayFinderResponseDTO.success();
    }

    private void saveCorpUser(CorpUserRequest corpUserRequest) {
        CorpUser corpUser = new CorpUser();
        corpUser.setBusinessAddress(corpUserRequest.getBusinessAddress());
        corpUser.setBusinessCategory(corpUserRequest.getBusinessCategory());
        corpUser.setBusinessName(corpUserRequest.getBusinessName());
        corpUser.setBusinessLicense(corpUserRequest.getBusinessLicense());
        corpUser.setBusinessTitle(corpUserRequest.getBusinessTitle());
        corpUser.setCorpStatus(CorpStatus.REGISTERED);
        log.info("Saving corpUser: {}", corpUser);

        corpUserRepository.save(corpUser);
    }
}