package com.vacation.platform.stayfinder.terms.controller;


import com.vacation.platform.stayfinder.common.ErrorType;
import com.vacation.platform.stayfinder.common.StayFinderException;
import com.vacation.platform.stayfinder.terms.dto.TermsDto;
import com.vacation.platform.stayfinder.terms.entity.Terms;
import com.vacation.platform.stayfinder.terms.entity.TermsSub;
import com.vacation.platform.stayfinder.terms.service.TermsService;
import com.vacation.platform.stayfinder.util.StayFinderResponseDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/*
* 약관 컨트롤러
* */

@RestController
@RequestMapping("/api/v1/terms")
@RequiredArgsConstructor
@Slf4j
public class TermsController {


    private final TermsService termsService;

    //처음에 약관 동의 하는 api 전송
    //특정 고객이 약관 상세 보고 싶다면 해당 약관 sub 전송
    //메인 동의 api 에서 응답으로 필수 동의를 전체 동의 한 경우에 휴대폰 인증으로 넘어감

    @GetMapping("/main")
    public ResponseEntity<StayFinderResponseDTO<List<Terms>>> getMain() {
        return termsService.getTermsMain();
    }

    //Get 으로 처리
    // TermsSub만 받음
    // Response 객체로 리턴
    @PostMapping("/sub")
    public ResponseEntity<StayFinderResponseDTO<List<TermsSub>>> getSub(@Valid @RequestBody TermsDto termsDto) {
        if(termsDto == null)
            throw new StayFinderException(ErrorType.DTO_NOT_FOUND,
                    "null pointer",
                    x -> log.error("{}", ErrorType.DTO_NOT_FOUND.getInternalMessage()),
                    null);

        return termsService.getTermsSub(termsDto);
    }




}
