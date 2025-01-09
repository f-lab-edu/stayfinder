package com.vacation.platform.stayfinder.certify.controller;

import com.vacation.platform.stayfinder.certify.service.CertifyService;
import com.vacation.platform.stayfinder.certify.service.serviceImpl.CertifyServiceImpl;
import com.vacation.platform.stayfinder.util.Result;
import jakarta.validation.Valid;
import net.nurigo.sdk.NurigoApp;
import net.nurigo.sdk.message.service.DefaultMessageService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/certify")
public class CertifyController {

    // 휴대폰 인증 요청 하고 응답 받으면
    // 유저 회원 컨트롤러로 넘긴다.
    private final DefaultMessageService defaultMessageService;


    @Value("${aes256.key}")
    private String key;

    @Value("${aes256.iv}")
    private String iv;

    private final CertifyService certifyService;

    public CertifyController(@Value("${nurigo.app.apikey}") String apiKey,
                             @Value("${nurigo.app.secret_key}") String secretKey,
                             @Value("${nurigo.app.domain}") String domain,
                             CertifyServiceImpl certifyService) {
        this.defaultMessageService = NurigoApp.INSTANCE.initialize(apiKey, secretKey, domain);
        this.certifyService = certifyService;
    }


    @PostMapping("req_send")
    public Result<?> reqSend(@Valid @RequestBody String certifyTarget) {
        return certifyService.reqSend(certifyTarget);
    }

}
