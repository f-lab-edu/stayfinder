package com.vacation.platform.corp.admin.service.impl;

import com.vacation.platform.api.common.ErrorType;
import com.vacation.platform.api.common.StayFinderException;
import com.vacation.platform.api.util.DateTimeUtil;
import com.vacation.platform.api.util.StayFinderResponseDTO;
import com.vacation.platform.corp.admin.dto.AdminRequestDTO;
import com.vacation.platform.corp.admin.service.AdminService;
import com.vacation.platform.corp.corperation.entity.CorpStatus;
import com.vacation.platform.corp.corperation.entity.Corporation;
import com.vacation.platform.corp.corperation.entity.CorporationRequest;
import com.vacation.platform.corp.corperation.entity.room.RequestStatus;
import com.vacation.platform.corp.corperation.repository.CorpUserRequestRepository;
import com.vacation.platform.corp.corperation.repository.CorporationRepository;
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

    private final CorporationRepository corporationRepository;


    @Override
    public StayFinderResponseDTO<?> authorityInquiry(AdminRequestDTO.CorporationRequestInquiryDTO corporationRequestInquiryDTO) {

        LocalDateTime startDate = corporationRequestInquiryDTO.getStartDate() != null ?
                DateTimeUtil.parseStartDate(corporationRequestInquiryDTO.getStartDate()) : LocalDateTime.now();

        LocalDateTime endDate = corporationRequestInquiryDTO.getEndDate() != null ?
                DateTimeUtil.parseEndDate(corporationRequestInquiryDTO.getEndDate()) : LocalDateTime.now();

        if(startDate.isAfter(endDate)) {
            throw new StayFinderException(ErrorType.INVALID_DATE_RANGE,
                    Map.of("corpUserRequestInquiryDTO", corporationRequestInquiryDTO),
                    log::error);
        }

        List<CorporationRequest> resultList
                = corpUserRequestRepository.findByCorpUserRequests(startDate, endDate,
                corporationRequestInquiryDTO.getRequestStatus() == null ? RequestStatus.PENDING : RequestStatus.getRequestStatus(corporationRequestInquiryDTO.getRequestStatus()));

        if(resultList.isEmpty()) {
            return StayFinderResponseDTO.success("데이터가 존재하지 않습니다.");
        }

        return StayFinderResponseDTO.success(resultList);
    }

    @Override
    @Transactional
    public StayFinderResponseDTO<?> approved(AdminRequestDTO.CorporationRequestApprovedDTO corporationRequestApprovedDTO) {
        CorporationRequest corporationRequest = corpUserRequestRepository.findById(corporationRequestApprovedDTO.getCorpUserId())
                .orElseThrow( () -> new StayFinderException(ErrorType.BUSINESS_LICENSE_IS_EMPTY,
                        Map.of("businessLicenseId", corporationRequestApprovedDTO.getCorpUserId()),
                        log::error));

        RequestStatus requestStatus = RequestStatus.getRequestStatus(corporationRequestApprovedDTO.getRequestStatus());

        corporationRequest.setStatus(requestStatus);
        corporationRequest.setApprovedAt(LocalDateTime.now());
        if(requestStatus.compareTo(RequestStatus.APPROVED) == 0) {
            try {
                saveCorpUser(corporationRequest);
            } catch (Exception e) {
                throw new StayFinderException(ErrorType.DB_ERROR,
                        Map.of("corpUser", corporationRequest),
                        log::error,
                        e);
            }
        }

        return StayFinderResponseDTO.success();
    }

    private void saveCorpUser(CorporationRequest corporationRequest) {
        Corporation corporation = new Corporation();
        corporation.setBusinessAddress(corporationRequest.getBusinessAddress());
        corporation.setBusinessCategory(corporationRequest.getBusinessCategory());
        corporation.setBusinessName(corporationRequest.getBusinessName());
        corporation.setBusinessLicense(corporationRequest.getBusinessLicense());
        corporation.setBusinessTitle(corporationRequest.getBusinessTitle());
        corporation.setCorpStatus(CorpStatus.REGISTERED);
        log.info("Saving corporation: {}", corporation);

        corporationRepository.save(corporation);
    }
}