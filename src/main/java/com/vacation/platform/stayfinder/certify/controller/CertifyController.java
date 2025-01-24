package com.vacation.platform.stayfinder.certify.controller;

import com.vacation.platform.stayfinder.certify.dto.CertifyRequestDto;
import com.vacation.platform.stayfinder.certify.service.CertifyService;
import com.vacation.platform.stayfinder.common.ErrorType;
import com.vacation.platform.stayfinder.common.StayFinderException;
import com.vacation.platform.stayfinder.util.Result;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/v1/certify")
@AllArgsConstructor
public class CertifyController {

    // 휴대폰 인증 요청 하고 응답 받으면
    // 유저 회원 컨트롤러로 넘긴다.
    private final CertifyService certifyService;

    /*
    * req_send로 들어올때는 약관 동의 내용, 핸드폰 번호를 입력 받아야 한다.
    * */

    @PostMapping("/req_send")
    public Result<?> reqSend(@Valid @RequestBody CertifyRequestDto certifyRequestDto) {
        if(certifyRequestDto == null) throw new StayFinderException(ErrorType.DTO_NOT_FOUND);
        return certifyService.reqSend(certifyRequestDto);
    }

}
