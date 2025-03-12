package com.vacation.platform.api.terms.controller;


import com.vacation.platform.api.common.ErrorType;
import com.vacation.platform.api.common.StayFinderException;
import com.vacation.platform.api.terms.dto.TermsDto;
import com.vacation.platform.api.terms.service.TermsService;
import com.vacation.platform.api.util.StayFinderResponseDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

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
    public StayFinderResponseDTO<List<TermsDto.MainResponseDto>> getMain() {
        return termsService.getTermsMain();
    }

    @GetMapping("/sub")
    public StayFinderResponseDTO<List<TermsDto.SubResponseDto>> getSub(@Valid @RequestBody TermsDto termsDto) {
        if(termsDto == null)
            throw new StayFinderException(ErrorType.DTO_NOT_FOUND,
                    Map.of("error", "termsDto 가 존재하지 않습니다."),
                    log::error);

        return termsService.getTermsSub(termsDto);
    }




}
