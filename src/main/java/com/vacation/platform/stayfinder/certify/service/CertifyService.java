package com.vacation.platform.stayfinder.certify.service;

import com.vacation.platform.stayfinder.util.Result;
import org.springframework.stereotype.Service;

@Service
public interface CertifyService {

    Result<?> reqSend(String certifyTarget);

}
