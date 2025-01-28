package com.vacation.platform.stayfinder.certify.service.serviceImpl;

import com.vacation.platform.stayfinder.certify.repository.CertifyReqSequenceRepository;
import com.vacation.platform.stayfinder.certify.repository.TermsUserAgreementSequenceRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class SequenceService {

    private final CertifyReqSequenceRepository certifyReqSequenceRepository;

    private final TermsUserAgreementSequenceRepository termsUserAgreementSequenceRepository;

    public SequenceService(CertifyReqSequenceRepository certifyReqSequenceRepository,
                           TermsUserAgreementSequenceRepository termsUserAgreementSequenceRepository) {
        this.certifyReqSequenceRepository = certifyReqSequenceRepository;
        this.termsUserAgreementSequenceRepository = termsUserAgreementSequenceRepository;
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

}
