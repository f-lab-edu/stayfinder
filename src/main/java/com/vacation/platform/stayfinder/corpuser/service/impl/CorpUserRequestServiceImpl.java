package com.vacation.platform.stayfinder.corpuser.service.impl;

import com.vacation.platform.stayfinder.common.ErrorType;
import com.vacation.platform.stayfinder.common.StayFinderException;
import com.vacation.platform.stayfinder.corpuser.dto.CorpUserRequestDTO;
import com.vacation.platform.stayfinder.corpuser.entity.BusinessCategory;
import com.vacation.platform.stayfinder.corpuser.entity.CorpUserRequest;
import com.vacation.platform.stayfinder.corpuser.entity.RequestStatus;
import com.vacation.platform.stayfinder.corpuser.repository.CorpUserRequestRepository;
import com.vacation.platform.stayfinder.corpuser.service.CorpUserRequestService;
import com.vacation.platform.stayfinder.util.StayFinderResponseDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class CorpUserRequestServiceImpl implements CorpUserRequestService {

    private final CorpUserRequestRepository corpUserRequestRepository;

    @Override
    public StayFinderResponseDTO<?> approvalRequest(CorpUserRequestDTO corpUserRequestDTO) {

        corpUserRequestRepository.findByBusinessLicense(corpUserRequestDTO.getBusinessLicense(), RequestStatus.REJECTED)
                .ifPresent(request -> {
                    throw new StayFinderException(
                            ErrorType.CORP_USER_REQUEST_DUPLICATION,
                            Map.of("businessLicense", corpUserRequestDTO.getBusinessLicense()),
                            log::error
                    );
                });


        try {
            ModelMapper modelMapper = new ModelMapper();

            CorpUserRequest corpUserRequest = modelMapper.map(corpUserRequestDTO, CorpUserRequest.class);

            corpUserRequest.setBusinessCategory(BusinessCategory.getByDesc(corpUserRequestDTO.getBusinessCategory()));

            corpUserRequestRepository.save(corpUserRequest);
        } catch(Exception ex) {
            throw new StayFinderException(ErrorType.DB_ERROR,
                    Map.of("corpUserRequestDTO", corpUserRequestDTO),
                    log::error);
        }

        return StayFinderResponseDTO.success();
    }
}
