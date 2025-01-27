package com.vacation.platform.stayfinder.user.service.serviceImpl;

import com.vacation.platform.stayfinder.certify.dto.CertifyRequestDto;
import com.vacation.platform.stayfinder.certify.entity.CertifyReq;
import com.vacation.platform.stayfinder.certify.entity.CertifyType;
import com.vacation.platform.stayfinder.certify.entity.TermsUserAgreement;
import com.vacation.platform.stayfinder.certify.repository.CertifyRepository;
import com.vacation.platform.stayfinder.certify.repository.TermsUserAgreementRepository;
import com.vacation.platform.stayfinder.common.ErrorType;
import com.vacation.platform.stayfinder.common.RedisTemporaryStorageService;
import com.vacation.platform.stayfinder.common.StayFinderException;
import com.vacation.platform.stayfinder.terms.dto.TermsDto;
import com.vacation.platform.stayfinder.user.dto.UserDTO;
import com.vacation.platform.stayfinder.user.entity.User;
import com.vacation.platform.stayfinder.user.repository.UserRepository;
import com.vacation.platform.stayfinder.user.service.UserService;
import com.vacation.platform.stayfinder.util.AES256Util;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
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


    public UserServiceImpl(UserRepository userRepository,
                           RedisTemporaryStorageService redisTemporaryStorageService,
                           CertifyRepository certifyRepository,
                           TermsUserAgreementRepository termsUserAgreementRepository) {
        this.userRepository = userRepository;
        this.redisTemporaryStorageService = redisTemporaryStorageService;
        this.certifyRepository = certifyRepository;
        this.termsUserAgreementRepository = termsUserAgreementRepository;
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
        // 패스워드 hash 암호화 설정
        user.setPassword(userDTO.getPassword());

        userRepository.save(user);

        User resultUser = userRepository.findByNickName(user.getNickName()).orElseThrow( () -> {
           throw new StayFinderException(ErrorType.CERTIFY_PHONE_NUM_NOT_MATCHED,
                   userDTO.getPhoneNumber(),
                   x -> log.error("{}", ErrorType.CERTIFY_PHONE_NUM_NOT_MATCHED.getInternalMessage()),
                   null);
        });

        CertifyReq certifyReq = modelMapper.map(certifyDtoResult, CertifyReq.class);
        certifyReq.setUserId(resultUser.getUserId());
        certifyReq.setCertifyNumber(CertifyType.PHONE);

        certifyRepository.save(certifyReq);

        for(TermsDto terms : certifyDtoResult.getTermsDtoList()) {
            TermsUserAgreement termsUserAgreement = new TermsUserAgreement();
            termsUserAgreement.setUserId(resultUser.getUserId());
            termsUserAgreement.setTermsId(terms.getTermsMainId());
            termsUserAgreement.setVersion(terms.getTermsMainId());
            termsUserAgreementRepository.save(termsUserAgreement);
        }


    }

//    @Override
//    public ResponseEntity<StayFinderResponseDTO<?>> modifyUser(UserDTO.saveDTO modifyDTO) {
//        return ResponseEntity.ok(StayFinderResponseDTO.success());
//    }


}
