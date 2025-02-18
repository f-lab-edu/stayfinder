package com.vacation.platform.stayfinder.corpuser.service.impl;

import com.vacation.platform.stayfinder.common.ErrorType;
import com.vacation.platform.stayfinder.common.StayFinderException;
import com.vacation.platform.stayfinder.corpuser.dto.CorpUserRequestDTO;
import com.vacation.platform.stayfinder.corpuser.entity.CorpUserRequest;
import com.vacation.platform.stayfinder.corpuser.entity.RequestStatus;
import com.vacation.platform.stayfinder.corpuser.repository.CorpUserRequestRepository;
import com.vacation.platform.stayfinder.corpuser.service.CorpUserRequestService;
import com.vacation.platform.stayfinder.util.StayFinderResponseDTO;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class CorpUserRequestServiceImpl implements CorpUserRequestService {

    private final CorpUserRequestRepository corpUserRequestRepository;

    private final CorpUserDBService corpUserDBService;

    @Override
    @Transactional
    public StayFinderResponseDTO<?> approvalRequest(CorpUserRequestDTO corpUserRequestDTO, List<MultipartFile> files) {
        corpUserRequestRepository.findByBusinessLicense(corpUserRequestDTO.getBusinessLicense(), RequestStatus.REJECTED)
                .ifPresent(request -> {
                    throw new StayFinderException(
                            ErrorType.CORP_USER_REQUEST_DUPLICATION,
                            Map.of("businessLicense", corpUserRequestDTO.getBusinessLicense()),
                            log::error
                    );
                });

        List<String> result;

        try {
            corpUserDBService.corpUserRequestSave(corpUserRequestDTO);

            CorpUserRequest corpUser = corpUserRequestRepository.findByBusinessLicense(corpUserRequestDTO.getBusinessLicense())
                    .orElseThrow(
                            () ->  new StayFinderException(
                                    ErrorType.DB_ERROR,
                                    Map.of("businessLicense", corpUserRequestDTO.getBusinessLicense()),
                                    log::error
                            ));

           result = corpUserDBService.corpUserBusinessLicenseFileSave(files, corpUser.getRequestId());
        } catch (IOException io) {
            throw new StayFinderException(ErrorType.FILE_ERROR,
                    Map.of("files", files),
                    log::error);
        } catch(Exception ex) {
            throw new StayFinderException(ErrorType.DB_ERROR,
                    Map.of("corpUserRequestDTO", corpUserRequestDTO),
                    log::error);
        }

        return StayFinderResponseDTO.success(result);
    }
}
