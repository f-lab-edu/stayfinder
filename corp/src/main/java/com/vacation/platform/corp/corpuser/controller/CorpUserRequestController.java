package com.vacation.platform.corp.corpuser.controller;

import com.vacation.platform.api.common.ErrorType;
import com.vacation.platform.api.common.StayFinderException;
import com.vacation.platform.api.util.StayFinderResponseDTO;
import com.vacation.platform.corp.corpuser.dto.CorpUserRequestDTO;
import com.vacation.platform.corp.corpuser.service.CorpUserRequestService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/api/v1/corp/request")
@RequiredArgsConstructor
public class CorpUserRequestController {

    private final CorpUserRequestService corpUserRequestService;

    @PostMapping(value = "/approval", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public StayFinderResponseDTO<?> approvalRequest(@RequestPart("data") CorpUserRequestDTO corpUserRequestDTO,
                                                    @RequestPart("files") List<MultipartFile> files) {

        if(files ==  null || files.isEmpty()) {
            throw new StayFinderException(ErrorType.BUSINESS_LICENSE_NOT_VALID,
                    Map.of("BusinessLicense", "File Not Found"),
                    log::error);
        }

        return corpUserRequestService.approvalRequest(corpUserRequestDTO, files);
    }

}
