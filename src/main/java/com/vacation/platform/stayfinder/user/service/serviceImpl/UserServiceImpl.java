package com.vacation.platform.stayfinder.user.service.serviceImpl;

import com.vacation.platform.stayfinder.certify.dto.CertifyRequestDto;
import com.vacation.platform.stayfinder.certify.entity.CertifyReq;
import com.vacation.platform.stayfinder.certify.entity.CertifyType;
import com.vacation.platform.stayfinder.certify.entity.TermsUserAgreement;
import com.vacation.platform.stayfinder.certify.repository.CertifyRepository;
import com.vacation.platform.stayfinder.certify.repository.TermsUserAgreementRepository;
import com.vacation.platform.stayfinder.certify.service.serviceImpl.SequenceService;
import com.vacation.platform.stayfinder.common.ErrorType;
import com.vacation.platform.stayfinder.common.RedisTemporaryStorageService;
import com.vacation.platform.stayfinder.common.StayFinderException;
import com.vacation.platform.stayfinder.terms.dto.TermsDto;
import com.vacation.platform.stayfinder.user.dto.UserDTO;
import com.vacation.platform.stayfinder.user.entity.Gender;
import com.vacation.platform.stayfinder.user.entity.Role;
import com.vacation.platform.stayfinder.user.entity.User;
import com.vacation.platform.stayfinder.user.entity.UserStatus;
import com.vacation.platform.stayfinder.user.repository.UserRepository;
import com.vacation.platform.stayfinder.user.service.UserService;
import com.vacation.platform.stayfinder.util.AES256Util;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class UserServiceImpl implements UserService {

    @Value("${aes256.key}")
    private String key;

    @Value("${aes256.iv}")
    private String iv;

    private final UserRepository userRepository;

    private final CertifyRepository certifyRepository;

    private final TermsUserAgreementRepository termsUserAgreementRepository;

    private final RedisTemporaryStorageService redisTemporaryStorageService;

    private final SequenceService sequenceService;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;


    public UserServiceImpl(UserRepository userRepository,
                           RedisTemporaryStorageService redisTemporaryStorageService,
                           CertifyRepository certifyRepository,
                           TermsUserAgreementRepository termsUserAgreementRepository,
                           SequenceService sequenceService,
                           BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.redisTemporaryStorageService = redisTemporaryStorageService;
        this.certifyRepository = certifyRepository;
        this.termsUserAgreementRepository = termsUserAgreementRepository;
        this.sequenceService = sequenceService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    @Transactional
    public void saveUser(UserDTO.saveDTO userDTO) {

        userRepository.findByNickName(userDTO.getNickName())
                .ifPresent(user -> {
                    throw new StayFinderException(ErrorType.DUPLICATE_NICK_NAME,
                            userDTO,
                            x -> log.error("{}", ErrorType.DUPLICATE_NICK_NAME.getInternalMessage()),
                            null);
                });

        userRepository.findByEmail(userDTO.getEmail())
                .ifPresent(user -> {
                    throw new StayFinderException(ErrorType.DUPLICATE_EMAIL,
                            userDTO,
                            x -> log.error("{}", ErrorType.DUPLICATE_EMAIL.getInternalMessage()),
                            null);
                });

        CertifyRequestDto certifyDtoResult = (CertifyRequestDto) redisTemporaryStorageService.getTemporaryData(userDTO.getPhoneNumber());

        log.info("certifyDtoResult {}", certifyDtoResult);

        if(certifyDtoResult == null) {
            throw new StayFinderException(ErrorType.USER_PHONE_NUM_NOT_MATCHED,
                    userDTO,
                    x -> log.error("{}", ErrorType.USER_PHONE_NUM_NOT_MATCHED.getInternalMessage()),
                    null);
        }


        if(!certifyDtoResult.getIsCertify()) {
            throw new StayFinderException(ErrorType.CERTIFY_IS_NOT_COMPLETE,
                    userDTO,
                    x -> log.error("{}", ErrorType.CERTIFY_IS_NOT_COMPLETE.getInternalMessage()),
                    null);
        }

        try {
            String decPhoneNum = AES256Util.decrypt(certifyDtoResult.getPhoneNumber(), key, iv);

            if(!decPhoneNum.equals(userDTO.getPhoneNumber())) {
                throw new StayFinderException(ErrorType.CERTIFY_PHONE_NUM_NOT_MATCHED,
                        userDTO.getPhoneNumber(),
                        x -> log.error("{}", ErrorType.CERTIFY_PHONE_NUM_NOT_MATCHED.getInternalMessage()),
                        null);
            }

        } catch(Exception e) {
            throw new StayFinderException(ErrorType.SYSTEM_ERROR,
                    userDTO.getPhoneNumber(),
                    x -> log.error("{}", e.getMessage()),
                    e);
        }

        ModelMapper modelMapper = new ModelMapper();

        User user = modelMapper.map(userDTO, User.class);

        try {
            user.setPassword(bCryptPasswordEncoder.encode(userDTO.getPassword()));
            user.setGender(Gender.getCode(userDTO.getGender()));
            user.setUserStatus(UserStatus.REGISTERED);
            user.setRole(Role.USER);
            log.info("user {}", user);
            userRepository.saveAndFlush(user);

            User resultUser = userRepository.findByNickName(user.getNickName()).orElseThrow(
                    () -> new StayFinderException(ErrorType.CERTIFY_PHONE_NUM_NOT_MATCHED,
                    userDTO.getPhoneNumber(),
                    x -> log.error("{}", ErrorType.CERTIFY_PHONE_NUM_NOT_MATCHED.getInternalMessage()),
                    null));

            CertifyReq certifyReq =  new CertifyReq();
            Long certifyReqId = sequenceService.getNextCertifyReqId();
            certifyReq.setId(certifyReqId);
            certifyReq.setUserId(resultUser.getUserId());
            certifyReq.setCertifyNumber(CertifyType.PHONE);
            certifyReq.setReqCertifyNumber(String.valueOf(certifyDtoResult.getReqCertifyNumber()));
            certifyReq.setTryNumber(certifyDtoResult.getTryNumber());
            log.info("certifyReq {}", certifyReq);
            certifyRepository.saveAndFlush(certifyReq);

            for(TermsDto terms : certifyDtoResult.getTermsDtoList()) {
                log.info("terms {}", terms);
                TermsUserAgreement termsUserAgreement = new TermsUserAgreement();
                Long termsUserAgreementId = sequenceService.getNextTermsUserAgreementId();
                termsUserAgreement.setId(termsUserAgreementId);
                termsUserAgreement.setUserId(resultUser.getUserId());
                termsUserAgreement.setTermsId(terms.getTermsMainId());
                termsUserAgreement.setIsAgreement(terms.getIsAgreement());
                log.info("termsUserAgreement {}", termsUserAgreement);
                termsUserAgreementRepository.saveAndFlush(termsUserAgreement);
            }
        } catch (Exception e) {
            throw new StayFinderException(ErrorType.SYSTEM_ERROR,
                    userDTO,
                    x -> log.error("{}", e.getMessage()),
                    e);
        }
    }



//    @Override
//    public ResponseEntity<StayFinderResponseDTO<?>> modifyUser(UserDTO.saveDTO modifyDTO) {
//        return ResponseEntity.ok(StayFinderResponseDTO.success());
//    }

}
