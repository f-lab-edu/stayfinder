package com.vacation.platform.stayfinder.terms.controller;


import com.vacation.platform.stayfinder.terms.dto.TermsDto;
import com.vacation.platform.stayfinder.terms.entity.Terms;
import com.vacation.platform.stayfinder.terms.entity.TermsSub;
import com.vacation.platform.stayfinder.terms.service.TermsService;
import com.vacation.platform.stayfinder.terms.service.serviceImpl.TermsServiceImpl;
import com.vacation.platform.stayfinder.util.ResponseCode;
import com.vacation.platform.stayfinder.util.Result;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/*
* 약관 컨트롤러
* */

@RestController
@RequestMapping("/api/v1/terms_main")
@Slf4j
public class TermsController {

    private final TermsService termsService;

    public TermsController(TermsServiceImpl termsService) {
        this.termsService = termsService;
    }

    //처음에 약관 동의 하는 api 전송
    //특정 고객이 약관 상세 보고 싶다면 해당 약관 sub 전송
    //메인 동의 api 에서 응답으로 필수 동의를 전체 동의 한 경우에 휴대폰 인증으로 넘어감
    
    // 메인 약관 조회 메서드
    @PostMapping("/getMain")
    public Result<List<Terms>> getMain() {
        try {
            return termsService.getTermsMain();
        } catch(Exception e) {
            log.error("TermsController.getMain {}", e.getMessage());
            return new Result<>(ResponseCode.INTERNAL_SERVER_ERROR, ResponseCode.INTERNAL_SERVER_ERROR.getCustomMessage(), null);
        }
    }

    // 세부 약관 조회 메서드
    @PostMapping("/getSubTerms")
    public Result<List<TermsSub>> getTermsSub(@Valid @RequestBody TermsDto termsDto) {
        try {
            return termsService.getTermsSub(termsDto);
        } catch (Exception e) {
            log.error("TermsController.getTermsSub {}", e.getMessage());
            return new Result<>(ResponseCode.INTERNAL_SERVER_ERROR, ResponseCode.INTERNAL_SERVER_ERROR.getCustomMessage(), null);
        }
    }

    // 약관 등록 메서드
    @PostMapping("/terms_register")
    public Result<?> termsRegistration(@Valid @RequestBody TermsDto termsDto) {
        try {
            if(termsDto == null) return new Result<String>(ResponseCode.BAD_REQUEST, ResponseCode.DATA_NOT_FOUND.getCustomMessage(), null);

            return termsService.registerTerms(termsDto);
        } catch(Exception e) {
            log.error("TermsController.termsRegistration {}", e.getMessage());
            return new Result<>(ResponseCode.INTERNAL_SERVER_ERROR, ResponseCode.INTERNAL_SERVER_ERROR.getCustomMessage(), null);
        }
    }


}
