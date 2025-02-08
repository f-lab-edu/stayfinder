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
import jakarta.transaction.Transactional;
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

    @Transactional
    public StayFinderResponseDTO<?> reqSend(CertifyRequestDto certifyRequestDto) {
        CertifyRequestDto certifyDtoResult = (CertifyRequestDto) redisTemporaryStorageService.getTemporaryData(certifyRequestDto.getPhoneNumber());

        int tryNumber = getTryNumber(certifyRequestDto, certifyDtoResult);

        List<Terms> termsList = termsRepositorySupport.selectTermsMain();

        Map<Long, TermsDto> termsDtoMap = certifyRequestDto.getTermsDtoList().stream().collect(Collectors.toMap(TermsDto::getTermsMainId, dto -> dto));

        for(Terms terms : termsList) {
            TermsDto dto = termsDtoMap.get(terms.getTermsId());

            if(dto == null) {
                throw new StayFinderException(ErrorType.TERMS_NOT_AGREEMENT,
                        Map.of("TermsMainTitle", terms.getTermsMainTitle()),
                        log::error);
            }

            if(Boolean.TRUE.equals(terms.getIsTermsRequired()) && Boolean.FALSE.equals(dto.getIsAgreement())) {
                throw new StayFinderException(ErrorType.TERMS_DIDNT_AGREEMENT,
                        Map.of("TermsMainTitle", terms.getTermsMainTitle()),
                        log::error);
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
                    Map.of("responseDto", Objects.requireNonNull(responseDto)),
                    log::error,
                    nuri);
        } catch (Exception e) {
            throw new StayFinderException(ErrorType.SYSTEM_ERROR,
                    Map.of("certifyRequestDto", certifyRequestDto),
                    log::error,
                    e);
        } finally {
            certifyRequestDto.setIsCertify(false);
            log.info("reqSend certifyRequestDto{}", certifyRequestDto);
            log.info("reqSend phoneNumber{}", phoneNumber);
            redisTemporaryStorageService.saveTemporaryData(phoneNumber, certifyRequestDto, 3600);
        }

        return StayFinderResponseDTO.success(responseDto);

    }

    private static int getTryNumber(CertifyRequestDto certifyRequestDto, CertifyRequestDto certifyDtoResult) {
        int tryNumber = 0;
        if(certifyDtoResult != null) {
            if (certifyDtoResult.getTryNumber() >= 5) {
                throw new StayFinderException(ErrorType.CERTIFY_TRY_NUMBER,
                        Map.of("PhoneNumber", certifyRequestDto.getPhoneNumber()),
                        log::error);
            }
            tryNumber = certifyDtoResult.getTryNumber() == 0 ? 0 : certifyDtoResult.getTryNumber();
        }
        return tryNumber;
    }

    @Transactional
    public StayFinderResponseDTO<?> certifyNumberProve(CertifyRequestDto certifyRequestDto) {
        CertifyRequestDto certifyDtoResult = (CertifyRequestDto) redisTemporaryStorageService.getTemporaryData(certifyRequestDto.getPhoneNumber());

        try {
            if(certifyDtoResult == null) throw new Exception();

            if(certifyDtoResult.getIsCertify()) {
                throw new StayFinderException(ErrorType.CERTIFY_IS_COMPLETE,
                        Map.of("PhoneNumber", certifyRequestDto.getPhoneNumber()),
                        log::error);
            }

            if(!certifyDtoResult.getReqCertifyNumber().equals(certifyRequestDto.getReqCertifyNumber())) {
                throw new StayFinderException(ErrorType.CERTIFY_PHONE_NUM_NOT_MATCHED,
                        Map.of("PhoneNumber", certifyRequestDto.getPhoneNumber()),
                        log::error);
            }

            String decPhoneNum = AES256Util.decrypt(certifyDtoResult.getPhoneNumber(), key, iv);

            if(!certifyRequestDto.getPhoneNumber().equals(decPhoneNum)) {
                throw new Exception();
            }
        } catch (Exception e) {
            throw new StayFinderException(ErrorType.CERTIFY_NOT_VALID,
                    Map.of("PhoneNumber", certifyRequestDto.getPhoneNumber()),
                    log::error,
                    e);
        } finally {
            Objects.requireNonNull(certifyDtoResult).setIsCertify(true);

            log.info("certifyNumberProve certifyDtoResult {}", certifyDtoResult);
            log.info("certifyNumberProve certifyRequestDto.getPhoneNumber(){}", certifyRequestDto.getPhoneNumber());

            redisTemporaryStorageService.saveTemporaryData(certifyRequestDto.getPhoneNumber(), certifyDtoResult, 3600);
        }

        return StayFinderResponseDTO.success(certifyRequestDto.getPhoneNumber());
    }

    @Override
    public StayFinderResponseDTO<?> certifyDelete(CertifyRequestDto certifyRequestDto) {
        redisTemporaryStorageService.deleteTemporaryData(certifyRequestDto.getPhoneNumber());
        return StayFinderResponseDTO.success();
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
