package com.vacation.platform.stayfinder.certify.service.serviceImpl;

import com.vacation.platform.stayfinder.certify.dto.CertifyRequestDto;
import com.vacation.platform.stayfinder.certify.dto.CertifyResponseDto;
import com.vacation.platform.stayfinder.certify.service.CertifyService;
import com.vacation.platform.stayfinder.common.ErrorType;
import com.vacation.platform.stayfinder.common.RedisTemporaryStorageService;
import com.vacation.platform.stayfinder.common.StayFinderException;
import com.vacation.platform.stayfinder.terms.dto.TermsDto;
import com.vacation.platform.stayfinder.terms.entity.Terms;
import com.vacation.platform.stayfinder.terms.repository.TermsRepository;
import com.vacation.platform.stayfinder.util.Result;
import lombok.extern.slf4j.Slf4j;
import net.nurigo.sdk.NurigoApp;
import net.nurigo.sdk.message.exception.NurigoMessageNotReceivedException;
import net.nurigo.sdk.message.model.Message;
import net.nurigo.sdk.message.response.MultipleDetailMessageSentResponse;
import net.nurigo.sdk.message.service.DefaultMessageService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;
import java.util.Random;

@Slf4j
@Service
public class CertifyServiceImpl implements CertifyService {

    private final DefaultMessageService defaultMessageService;


    @Value("${aes256.key}")
    private String key;

    @Value("${aes256.iv}")
    private String iv;

    @Value("${nurigo.app.phone}")
    private String myPhoneNum;

    private final RedisTemporaryStorageService redisTemporaryStorageService;

    private final TermsRepository termsRepository;

    public CertifyServiceImpl(@Value("${nurigo.app.apikey}") String apiKey,
                              @Value("${nurigo.app.secret_key}") String secretKey,
                              @Value("${nurigo.app.domain}") String domain,
                              RedisTemporaryStorageService redisTemporaryStorageService,
                              TermsRepository termsRepository) {
        this.defaultMessageService = NurigoApp.INSTANCE.initialize(apiKey, secretKey, domain);
        this.redisTemporaryStorageService = redisTemporaryStorageService;
        this.termsRepository = termsRepository;
    }

    /**
     * 핸드폰 인증 요청
     * @param certifyRequestDto
     * @return
     */
    public Result<?> reqSend(CertifyRequestDto certifyRequestDto) {
        CertifyRequestDto resultDto = (CertifyRequestDto) redisTemporaryStorageService.getTemporaryData(certifyRequestDto.getPhoneNumber());
        Random random = new Random();

        if(resultDto != null) {
            if(resultDto.getTryNumber() >= 5) {
                throw new StayFinderException(ErrorType.CERTIFY_TRY_NUMBER);
            }

            if(resultDto.getTermsDtoList().size() == 5){

            }

            for(TermsDto userTermsDto : resultDto.getTermsDtoList()) {
                Optional<Terms> dto = termsRepository.findById(userTermsDto.getTermsMainId());
                dto.orElseThrow();
            }
        }



        int tryNumber = certifyRequestDto.getTryNumber() == 0 ? 0 : certifyRequestDto.getTryNumber();

        int certifyNumber = random.nextInt(100000);

        CertifyResponseDto responseDto;

        certifyRequestDto.setTryNumber(tryNumber + 1);

        try {
            responseDto = sendMessage(certifyRequestDto.getPhoneNumber(), certifyNumber);
        } catch (StayFinderException e) {
            throw new StayFinderException(e.getErrorType());
        } finally {
            redisTemporaryStorageService.saveTemporaryData(certifyRequestDto.getPhoneNumber(), certifyRequestDto, 3600);
        }


        return Result.success(responseDto);

    }

    /**
     * 핸드폰 인증 요청 API 발송
     * @param phoneNum
     * @param certifyNumber
     * @return CertifyResponseDto
     * @throws StayFinderException
     */
    private CertifyResponseDto sendMessage(String phoneNum, int certifyNumber) throws StayFinderException {
        Message message = new Message();
        StringBuilder sb = new StringBuilder();
        CertifyResponseDto result = new CertifyResponseDto();

        sb.append("인증번호 ");
        sb.append(certifyNumber);
        sb.append("를 입력해주세요.");

        message.setFrom(myPhoneNum);
        message.setTo(phoneNum);
        message.setText(sb.toString());

        try {
            MultipleDetailMessageSentResponse response = defaultMessageService.send(message);

            response.getFailedMessageList().forEach(item -> {
                if(Objects.requireNonNull(item.getStatusCode()).equals("2000")) {
                    result.setResponseCode(item.getStatusCode());
                }
                else {
                    result.setResponseCode(item.getStatusCode());
                    result.setResponseMessage(item.getStatusMessage());
                }
            });

        } catch (NurigoMessageNotReceivedException nuriException){
            log.error(nuriException.getMessage());
            throw new StayFinderException(ErrorType.Nurigo_ERROR);
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new StayFinderException(ErrorType.SYSTEM_ERROR);
        }
        return result;
    }

}
