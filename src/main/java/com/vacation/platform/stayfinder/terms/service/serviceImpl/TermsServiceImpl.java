package com.vacation.platform.stayfinder.terms.service.serviceImpl;

import com.vacation.platform.stayfinder.terms.dto.TermsDto;
import com.vacation.platform.stayfinder.terms.entity.Terms;
import com.vacation.platform.stayfinder.terms.entity.TermsSub;
import com.vacation.platform.stayfinder.terms.repository.TermsRepository;
import com.vacation.platform.stayfinder.terms.repository.TermsSubRepository;
import com.vacation.platform.stayfinder.terms.service.TermsService;
import com.vacation.platform.stayfinder.util.ResponseCode;
import com.vacation.platform.stayfinder.util.Result;
import org.springframework.stereotype.Service;

@Service
public class TermsServiceImpl implements TermsService {

    private final TermsRepository termsRepository;

    private final TermsSubRepository termsSubRepository;

    public TermsServiceImpl(TermsRepository termsRepository, TermsSubRepository termsSubRepository) {
        this.termsRepository = termsRepository;
        this.termsSubRepository = termsSubRepository;
    }

    @Override
    public Result<Terms> getTermsMain() {

        return null;
    }

    // 약관 저장 하는것부터 처리해야함
    // 필요한것은 약관 메인 타이틀, 약관 서브 타이틀
    // 약관 내용
    // 약관 버전

    @Override
    public Result<?> registerTerms(TermsDto termsDto) {
        if(termsDto == null) {
            return new Result<>(ResponseCode.BAD_REQUEST, ResponseCode.BAD_REQUEST.getCustomMessage(), null);
        }



        return null;
    }
}
