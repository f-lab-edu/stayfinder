package com.vacation.platform.stayfinder.certify.service.serviceImpl;

import com.vacation.platform.stayfinder.certify.repository.CertifyReqSequenceRepository;
import com.vacation.platform.stayfinder.certify.repository.TermsUserAgreementSequenceRepository;
import com.vacation.platform.stayfinder.login.repository.UserAuthSequenceRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class SequenceService {

    private final CertifyReqSequenceRepository certifyReqSequenceRepository;

    private final TermsUserAgreementSequenceRepository termsUserAgreementSequenceRepository;

    private final UserAuthSequenceRepository userAuthSequenceRepository;

    public SequenceService(CertifyReqSequenceRepository certifyReqSequenceRepository,
                           TermsUserAgreementSequenceRepository termsUserAgreementSequenceRepository,
                           UserAuthSequenceRepository userAuthSequenceRepository) {
        this.certifyReqSequenceRepository = certifyReqSequenceRepository;
        this.termsUserAgreementSequenceRepository = termsUserAgreementSequenceRepository;
        this.userAuthSequenceRepository = userAuthSequenceRepository;
    }

    @Transactional
    public Long getNextCertifyReqId() {
        certifyReqSequenceRepository.incrementSequence("certify_req_id");
        return certifyReqSequenceRepository.getNextSequenceValue("certify_req_id");
    }

    @Transactional
    public Long getNextTermsUserAgreementId() {
        termsUserAgreementSequenceRepository.incrementSequence("terms_user_agreement_id");
        return termsUserAgreementSequenceRepository.getNextSequenceValue("terms_user_agreement_id");
    }

    @Transactional
    public Long getNextUserAuthId() {
        userAuthSequenceRepository.incrementSequence("user_auth_id");
        return userAuthSequenceRepository.getNextSequenceValue("user_auth_id");
    }

}
