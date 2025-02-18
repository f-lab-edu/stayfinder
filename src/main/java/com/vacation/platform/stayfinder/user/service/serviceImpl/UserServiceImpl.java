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

import java.util.Map;

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
        this.userDTOValidation(userDTO);

        CertifyRequestDto certifyDtoResult = this.certifyRequestValidation(userDTO);

        try {
            String decPhoneNum = AES256Util.decrypt(certifyDtoResult.getPhoneNumber(), key, iv);

            if(!decPhoneNum.equals(userDTO.getPhoneNumber())) {
                throw new StayFinderException(ErrorType.CERTIFY_PHONE_NUM_NOT_MATCHED,
                        Map.of("phoneNumber", userDTO.getPhoneNumber()),
                        log::error);
            }

        } catch(Exception e) {
            throw new StayFinderException(ErrorType.SYSTEM_ERROR,
                    Map.of("phoneNumber", userDTO.getPhoneNumber()),
                    log::error,
                    e);
        }

        try {

            User resultUser = this.userSave(userDTO);

            this.certifyReqSave(resultUser, certifyDtoResult);

            certifyDtoResult.getTermsDtoList().forEach((termsDto) -> {
                termsUserAgreementSave(resultUser, termsDto);
            });
        } catch (Exception e) {
            throw new StayFinderException(ErrorType.SYSTEM_ERROR,
                    Map.of("userDTO", userDTO),
                    log::error,
                    e);
        }
    }

    private void userDTOValidation(UserDTO.saveDTO userDTO) {
        userRepository.findByNickName(userDTO.getNickName())
                .ifPresent(user -> {
                    throw new StayFinderException(ErrorType.DUPLICATE_NICK_NAME,
                            Map.of("NickName", user.getNickName()),
                            log::error);
                });

        userRepository.findByEmail(userDTO.getEmail())
                .ifPresent(user -> {
                    throw new StayFinderException(ErrorType.DUPLICATE_EMAIL,
                            Map.of("Email", user.getEmail()),
                            log::error);
                });
    }

    private CertifyRequestDto certifyRequestValidation(UserDTO.saveDTO userDTO) {
        CertifyRequestDto certifyDtoResult = (CertifyRequestDto) redisTemporaryStorageService.getTemporaryData(userDTO.getPhoneNumber());

        log.info("certifyDtoResult {}", certifyDtoResult);

        if(certifyDtoResult == null) {
            throw new StayFinderException(ErrorType.USER_PHONE_NUM_NOT_MATCHED,
                    Map.of("userDTO", userDTO),
                    log::error);
        }


        if(!certifyDtoResult.getIsCertify()) {
            throw new StayFinderException(ErrorType.CERTIFY_IS_NOT_COMPLETE,
                    Map.of("userDTO", userDTO),
                    log::error);
        }

        return certifyDtoResult;
    }

    protected User userSave(UserDTO.saveDTO userDTO) throws Exception {
        ModelMapper modelMapper = new ModelMapper();

        User user = modelMapper.map(userDTO, User.class);

        user.setPassword(bCryptPasswordEncoder.encode(userDTO.getPassword()));
        user.setGender(Gender.getCode(userDTO.getGender()));
        user.setUserStatus(UserStatus.REGISTERED);
        user.setRole(Role.ROLE_USER);

        log.info("user {}", user);
        userRepository.saveAndFlush(user);

        userRepository.flush();

        return userRepository.findByNickName(user.getNickName()).orElseThrow(
                () -> new StayFinderException(ErrorType.CERTIFY_PHONE_NUM_NOT_MATCHED,
                        Map.of("phoneNumber", userDTO.getPhoneNumber()),
                        log::error));
    }

    protected void certifyReqSave (User user, CertifyRequestDto certifyDtoResult) {
        CertifyReq certifyReq =  new CertifyReq();
        Long certifyReqId = sequenceService.getNextCertifyReqId();
        certifyReq.setId(certifyReqId);
        certifyReq.setUserId(user.getUserId());
        certifyReq.setCertifyNumber(CertifyType.PHONE);
        certifyReq.setReqCertifyNumber(String.valueOf(certifyDtoResult.getReqCertifyNumber()));
        certifyReq.setTryNumber(certifyDtoResult.getTryNumber());
        log.info("certifyReq {}", certifyReq);
        certifyRepository.saveAndFlush(certifyReq);
    }

    protected void termsUserAgreementSave(User user, TermsDto termsDto) {
        log.info("terms {}", termsDto);
        TermsUserAgreement termsUserAgreement = new TermsUserAgreement();
        Long termsUserAgreementId = sequenceService.getNextTermsUserAgreementId();
        termsUserAgreement.setId(termsUserAgreementId);
        termsUserAgreement.setUserId(user.getUserId());
        termsUserAgreement.setTermsId(termsDto.getTermsMainId());
        termsUserAgreement.setIsAgreement(termsDto.getIsAgreement());
        log.info("termsUserAgreement {}", termsUserAgreement);
        termsUserAgreementRepository.saveAndFlush(termsUserAgreement);
    }
}
