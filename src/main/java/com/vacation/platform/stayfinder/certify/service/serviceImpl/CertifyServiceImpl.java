package com.vacation.platform.stayfinder.certify.service.serviceImpl;

import com.vacation.platform.stayfinder.certify.dto.CertifyRequestDto;
import com.vacation.platform.stayfinder.certify.dto.CertifyResponseDto;
import com.vacation.platform.stayfinder.certify.service.CertifyService;
import com.vacation.platform.stayfinder.common.ErrorType;
import com.vacation.platform.stayfinder.common.RedisTemporaryStorageService;
import com.vacation.platform.stayfinder.common.StayFinderException;
import com.vacation.platform.stayfinder.terms.dto.TermsDto;
import com.vacation.platform.stayfinder.terms.entity.Terms;
import com.vacation.platform.stayfinder.terms.repository.support.TermsRepositorySupport;
import com.vacation.platform.stayfinder.util.AES256Util;
import com.vacation.platform.stayfinder.util.StayFinderResponseDTO;
import lombok.extern.slf4j.Slf4j;
import net.nurigo.sdk.NurigoApp;
import net.nurigo.sdk.message.exception.NurigoEmptyResponseException;
import net.nurigo.sdk.message.exception.NurigoMessageNotReceivedException;
import net.nurigo.sdk.message.exception.NurigoUnknownException;
import net.nurigo.sdk.message.model.Message;
import net.nurigo.sdk.message.request.MessageListRequest;
import net.nurigo.sdk.message.response.MessageListResponse;
import net.nurigo.sdk.message.response.MultipleDetailMessageSentResponse;
import net.nurigo.sdk.message.service.DefaultMessageService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Random;
import java.util.stream.Collectors;

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

    private final TermsRepositorySupport termsRepositorySupport;

    public CertifyServiceImpl(@Value("${nurigo.app.apikey}") String apiKey,
                              @Value("${nurigo.app.secret_key}") String secretKey,
                              @Value("${nurigo.app.domain}") String domain,
                              RedisTemporaryStorageService redisTemporaryStorageService,
                              TermsRepositorySupport termsRepositorySupport) {
        this.defaultMessageService = NurigoApp.INSTANCE.initialize(apiKey, secretKey, domain);
        this.redisTemporaryStorageService = redisTemporaryStorageService;
        this.termsRepositorySupport = termsRepositorySupport;
    }

    public ResponseEntity<StayFinderResponseDTO<?>> reqSend(CertifyRequestDto certifyRequestDto) {
        CertifyRequestDto certifyDtoResult = (CertifyRequestDto) redisTemporaryStorageService.getTemporaryData(certifyRequestDto.getPhoneNumber());

        int tryNumber = 0;
        if(certifyDtoResult != null) {
            if (certifyDtoResult.getTryNumber() >= 5) {
                throw new StayFinderException(ErrorType.CERTIFY_TRY_NUMBER,
                        certifyRequestDto.getPhoneNumber(),
                        x -> log.error("{}", ErrorType.CERTIFY_TRY_NUMBER.getInternalMessage()),
                        null);
            }
            tryNumber = certifyDtoResult.getTryNumber() == 0 ? 0 : certifyDtoResult.getTryNumber();
        }

        List<Terms> termsList = termsRepositorySupport.selectTermsMain();

        Map<Long, TermsDto> termsDtoMap = certifyRequestDto.getTermsDtoList().stream().collect(Collectors.toMap(TermsDto::getTermsMainId, dto -> dto));

        for(Terms terms : termsList) {
            TermsDto dto = termsDtoMap.get(terms.getTermsId());

            if(dto == null) {
                throw new StayFinderException(ErrorType.TERMS_NOT_AGREEMENT,
                        terms.getTermsMainTitle(),
                        x -> log.error("{}", ErrorType.TERMS_NOT_AGREEMENT.getInternalMessage()),
                        null
                        );
            }

            if(Boolean.TRUE.equals(terms.getIsTermsRequired()) && Boolean.FALSE.equals(dto.getIsAgreement())) {
                throw new StayFinderException(ErrorType.TERMS_DIDNT_AGREEMENT,
                        terms.getTermsMainTitle(),
                        x -> log.error("{}", ErrorType.TERMS_DIDNT_AGREEMENT.getInternalMessage()),
                        null);
            }

        }

        Integer certifyNumber = generateRandomNumber();

        CertifyResponseDto responseDto = new CertifyResponseDto();

        certifyRequestDto.setTryNumber(tryNumber + 1);

        certifyRequestDto.setReqCertifyNumber(certifyNumber);

        String phoneNumber = certifyRequestDto.getPhoneNumber();

        try {
            responseDto = sendMessage(certifyRequestDto.getPhoneNumber(), certifyNumber);

            responseDto.setCertifyNumber(certifyNumber);
            responseDto.setPhoneNumber(phoneNumber);

            certifyRequestDto.setPhoneNumber(AES256Util.encrypt(certifyRequestDto.getPhoneNumber(), key, iv));
        } catch (NurigoMessageNotReceivedException | NurigoEmptyResponseException | NurigoUnknownException nuri) {
            throw new StayFinderException(ErrorType.Nurigo_ERROR,
                    Objects.requireNonNull(responseDto),
                    x -> log.error("{}", nuri.getMessage()),
                    nuri);
        } catch (Exception e) {
            throw new StayFinderException(ErrorType.SYSTEM_ERROR,
                    certifyRequestDto,
                    x -> log.error("{}", e.getMessage()),
                    e);
        } finally {
            certifyRequestDto.setIsCertify(false);
            redisTemporaryStorageService.saveTemporaryData(phoneNumber, certifyRequestDto, 3600);
        }


        return ResponseEntity.ok(StayFinderResponseDTO.success(responseDto));

    }

    public ResponseEntity<StayFinderResponseDTO<?>> certifyNumberProve(CertifyRequestDto certifyRequestDto) {
        CertifyRequestDto certifyDtoResult = (CertifyRequestDto) redisTemporaryStorageService.getTemporaryData(certifyRequestDto.getPhoneNumber());

        try {
            if(certifyDtoResult == null) throw new Exception();

            if(certifyDtoResult.getIsCertify()) {
                throw new StayFinderException(ErrorType.CERTIFY_IS_COMPLETE,
                        certifyRequestDto.getPhoneNumber(),
                        x -> log.error("{}", ErrorType.CERTIFY_IS_COMPLETE.getInternalMessage()),
                        null);
            }

            if(!certifyDtoResult.getReqCertifyNumber().equals(certifyRequestDto.getReqCertifyNumber())) {
                throw new StayFinderException(ErrorType.CERTIFY_PHONE_NUM_NOT_MATCHED,
                        certifyRequestDto.getReqCertifyNumber(),
                        x -> log.error("{}", ErrorType.CERTIFY_PHONE_NUM_NOT_MATCHED.getInternalMessage()),
                        null);
            }

            String decPhoneNum = AES256Util.decrypt(certifyDtoResult.getPhoneNumber(), key, iv);

            if(!certifyRequestDto.getPhoneNumber().equals(decPhoneNum)) {
                throw new Exception();
            }

            certifyDtoResult.setIsCertify(true);

        } catch (Exception e) {
            throw new StayFinderException(ErrorType.CERTIFY_NOT_VALID,
                    certifyRequestDto.getPhoneNumber(),
                    x -> log.error("{}", ErrorType.CERTIFY_NOT_VALID),
                    e);
        } finally {
            redisTemporaryStorageService.saveTemporaryData(Objects.requireNonNull(certifyDtoResult).getPhoneNumber(), certifyDtoResult, 3600);
        }

        return ResponseEntity.ok(StayFinderResponseDTO.success(certifyRequestDto.getPhoneNumber()));
    }

    private CertifyResponseDto sendMessage(String phoneNum, int certifyNumber) throws StayFinderException, NurigoMessageNotReceivedException, NurigoEmptyResponseException, NurigoUnknownException {
        Message message = new Message();
        CertifyResponseDto result = new CertifyResponseDto();

        String sb = "인증번호 " +
                certifyNumber +
                "를 입력해주세요.";

        message.setFrom(myPhoneNum);
        message.setTo(phoneNum);
        message.setText(sb);

        MultipleDetailMessageSentResponse response = defaultMessageService.send(message);

        MessageListRequest messageListRequest = new MessageListRequest();
        messageListRequest.setGroupId(Objects.requireNonNull(response.getGroupInfo()).getGroupId());


        log.info("response {}", response);

        MessageListResponse messageResponse = defaultMessageService.getMessageList(messageListRequest);

        log.info("MessageListResponse {}",Objects.requireNonNull(messageResponse));

        result.setResponseCode("0000");
        result.setResponseMessage("요청 성공");

//        if(response.getFailedMessageList().size() >= 1) {
//            for(FailedMessage massage : Objects.requireNonNull(response.getFailedMessageList())) {
//                if(Objects.requireNonNull(message.getStatusCode()).equals("2000") || Objects.requireNonNull(message.getStatusCode()).equals("3059")) {
//                    result.setResponseCode("0000");
//                    result.setResponseMessage(massage.getStatusMessage());
//                }
//            }
//        }else  {
//            result.setResponseCode(ErrorType.Nurigo_ERROR.getCode());
//            result.setResponseMessage(ErrorType.Nurigo_ERROR.getExternalMessage());
//        }

        return result;
    }

    private Integer generateRandomNumber() {
        Random random = new Random();

        int firstDigit = random.nextInt(9) + 1;

        StringBuilder result = new StringBuilder();
        result.append(firstDigit);
        for (int i = 1; i < 6; i++) {
            result.append(random.nextInt(10));
        }

        return Integer.valueOf(result.toString());
    }

}
