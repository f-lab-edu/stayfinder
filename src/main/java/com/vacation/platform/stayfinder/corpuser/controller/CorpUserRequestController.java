package com.vacation.platform.stayfinder.corpuser.controller;

import com.vacation.platform.stayfinder.common.ErrorType;
import com.vacation.platform.stayfinder.common.StayFinderException;
import com.vacation.platform.stayfinder.corpuser.dto.CorpUserRequestDTO;
import com.vacation.platform.stayfinder.corpuser.service.CorpUserRequestService;
import com.vacation.platform.stayfinder.util.StayFinderResponseDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
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
