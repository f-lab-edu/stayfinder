package com.vacation.platform.stayfinder.certify.service.serviceImpl;

import com.vacation.platform.stayfinder.certify.repository.TermsUserAgreementRepository;
import com.vacation.platform.stayfinder.certify.service.TermsUserAgreementService;
import com.vacation.platform.stayfinder.terms.dto.TermsDto;
import com.vacation.platform.stayfinder.user.repository.UserRepository;
import com.vacation.platform.stayfinder.util.Result;
import org.springframework.stereotype.Service;

@Service
public class TermsUserAgreementServiceImpl implements TermsUserAgreementService {

    private final TermsUserAgreementRepository termsUserAgreementRepository;

    private final UserRepository userRepository;

    public TermsUserAgreementServiceImpl(TermsUserAgreementRepository termsUserAgreementRepository,
                                         UserRepository userRepository) {
        this.termsUserAgreementRepository = termsUserAgreementRepository;
        this.userRepository = userRepository;
    }

    // user id 생성후 저장
    @Override
    public Result<?> registerUserTerms(TermsDto termsDto) {
        //
        /*
         * 랜덤한 값으로 user_id값을 넣고 saveAndFlush
         * 후에 해당 user_id로 데이터를 찾은 다음에
         * yyMMdd + termsUserAgreement 테이블의 id와 더 한후에 user_id 업데이트 처리 및
         * 해당 user_id를 리턴
         */

        return Result.success();
    }
}
