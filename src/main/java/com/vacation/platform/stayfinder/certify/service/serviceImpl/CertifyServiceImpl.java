package com.vacation.platform.stayfinder.certify.service.serviceImpl;

import com.vacation.platform.stayfinder.certify.repository.CertifyRepository;
import com.vacation.platform.stayfinder.certify.service.CertifyService;
import com.vacation.platform.stayfinder.util.ResponseCode;
import com.vacation.platform.stayfinder.util.Result;
import net.nurigo.sdk.NurigoApp;
import net.nurigo.sdk.message.exception.NurigoMessageNotReceivedException;
import net.nurigo.sdk.message.model.Message;
import net.nurigo.sdk.message.response.MultipleDetailMessageSentResponse;
import net.nurigo.sdk.message.service.DefaultMessageService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Random;

@Service
public class CertifyServiceImpl implements CertifyService {

    private final DefaultMessageService defaultMessageService;


    @Value("${aes256.key}")
    private String key;

    @Value("${aes256.iv}")
    private String iv;

    @Value("${nurigo.app.phone}")
    private String myPhoneNum;

    private final CertifyRepository certifyRepository;

    public CertifyServiceImpl(@Value("${nurigo.app.apikey}") String apiKey,
                             @Value("${nurigo.app.secret_key}") String secretKey,
                             @Value("${nurigo.app.domain}") String domain,
                              CertifyRepository certifyRepository) {
        this.defaultMessageService = NurigoApp.INSTANCE.initialize(apiKey, secretKey, domain);
        this.certifyRepository = certifyRepository;
    }


    @Override
    public Result<?> reqSend(String certifyTarget) {
        Message message = new Message();
        StringBuffer sb = new StringBuffer();
        Random random = new Random();

        int certifyNumber = random.nextInt(100000);

        sb.append("인증번호 ");
        sb.append(certifyNumber);
        sb.append("를 입력해주세요.");

        message.setFrom(myPhoneNum);
        message.setTo(certifyTarget);
        message.setText(sb.toString());

        try {
            MultipleDetailMessageSentResponse response = defaultMessageService.send(message);

            response.getFailedMessageList().stream().forEach(item -> {
                if(Objects.requireNonNull(item.getStatusCode()).equals("2000")) {
                    // 성공시

                } else {
                    // 실패시
                }
            });

        } catch (NurigoMessageNotReceivedException nuriException){
            return Result.fail(ResponseCode.INTERNAL_SERVER_ERROR, nuriException.getMessage());
        } catch (Exception e) {
            return Result.fail(ResponseCode.INTERNAL_SERVER_ERROR, ResponseCode.INTERNAL_SERVER_ERROR.getCustomMessage());
        } finally {
            // db 저장 로직
        }

        return Result.success();

    }
}
