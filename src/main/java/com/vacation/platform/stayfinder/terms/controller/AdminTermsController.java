package com.vacation.platform.stayfinder.terms.controller;

import com.vacation.platform.stayfinder.common.ErrorType;
import com.vacation.platform.stayfinder.common.StayFinderException;
import com.vacation.platform.stayfinder.terms.dto.TermsDto;
import com.vacation.platform.stayfinder.terms.service.TermsService;
import com.vacation.platform.stayfinder.terms.service.serviceImpl.TermsServiceImpl;
import com.vacation.platform.stayfinder.util.StayFinderResponseDTO;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;


@Slf4j
@RestController
@RequestMapping("/api/v1/admin/terms")
public class AdminTermsController {

    private final TermsService termsService;

    public AdminTermsController(TermsServiceImpl termsService) {
        this.termsService = termsService;
    }

    @PostMapping("/register")
    public ResponseEntity<StayFinderResponseDTO<?>> termsRegistration(@Valid @RequestBody TermsDto termsDto) {
        if(termsDto == null)
            throw new StayFinderException(ErrorType.DTO_NOT_FOUND,
                    Map.of("error", "termsDto 가 존재하지 않습니다."),
                    log::error);

        termsService.registerTerms(termsDto);

        return ResponseEntity.ok(StayFinderResponseDTO.success());
    }

}
