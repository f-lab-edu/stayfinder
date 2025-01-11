package com.vacation.platform.stayfinder.certify.controller;

import com.vacation.platform.stayfinder.certify.service.CertifyService;
import com.vacation.platform.stayfinder.certify.service.serviceImpl.CertifyServiceImpl;
import com.vacation.platform.stayfinder.util.Result;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/certify")
public class CertifyController {

    // 휴대폰 인증 요청 하고 응답 받으면
    // 유저 회원 컨트롤러로 넘긴다.

    private final CertifyService certifyService;

    public CertifyController(CertifyServiceImpl certifyService) {
        this.certifyService = certifyService;
    }


    @PostMapping("req_send")
    public Result<?> reqSend(@Valid @RequestBody String certifyTarget) {
        return certifyService.reqSend(certifyTarget);
    }

}
