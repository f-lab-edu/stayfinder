package com.vacation.platform.stayfinder.terms.service.serviceImpl;

import com.vacation.platform.stayfinder.certify.repository.TermsUserAgreementRepository;
import com.vacation.platform.stayfinder.common.ErrorType;
import com.vacation.platform.stayfinder.common.StayFinderException;
import com.vacation.platform.stayfinder.terms.dto.TermsDto;
import com.vacation.platform.stayfinder.terms.entity.Terms;
import com.vacation.platform.stayfinder.terms.entity.TermsSub;
import com.vacation.platform.stayfinder.terms.repository.TermsRepository;
import com.vacation.platform.stayfinder.terms.repository.TermsSubRepository;
import com.vacation.platform.stayfinder.terms.service.TermsService;
import com.vacation.platform.stayfinder.util.Result;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class TermsServiceImpl implements TermsService {

    private final TermsRepository termsRepository;

    private final TermsSubRepository termsSubRepository;

    public TermsServiceImpl(TermsRepository termsRepository, TermsSubRepository termsSubRepository, TermsUserAgreementRepository termsUserAgreementRepository) {
        this.termsRepository = termsRepository;
        this.termsSubRepository = termsSubRepository;
    }

    @Override
    public Result<List<Terms>> getTermsMain() {

        List<Terms> termsList = termsRepository.findAll();

        if(termsList.size() > 0) {
            return Result.success(termsList);
        }

        return Result.success();
    }

    @Override
    public Result<List<TermsSub>> getTermsSub() {
        return null;
    }

    // 약관 저장 하는것부터 처리해야함
    // 필요한것은 약관 메인 타이틀, 약관 서브 타이틀
    // 약관 내용
    // 약관 버전

    @Override
    @Transactional
    public void registerTerms(TermsDto termsDto) {
        Optional<Terms> terms = termsRepository.findByTermsMainTile(termsDto.getMainTitle());



        if(  !termsDto.isCompulsion() && terms.isPresent()) {
            throw new StayFinderException(ErrorType.DUPLICATE_TERMS_TITLE);
        } else if(termsDto.isCompulsion() && terms.isPresent()) {
            // 수정 서비스로 토스
            termsModify(termsDto);
        }

        termsSave(termsDto);
    }


    @Transactional
    private void termsSave(TermsDto termsDto) {
        Terms terms = new Terms();
        TermsSub termsSub = new TermsSub();

        termsSub.setTermsDetailsTitle(termsDto.getSubTitle());
        termsSub.setTermsDetailsContent(termsDto.getDetailContent());
        termsSub.setVersion(1);
        terms.setTermsMainTile(termsDto.getMainTitle());

        try {
            termsRepository.save(terms);
            termsSubRepository.save(termsSub);
        } catch (Exception e) {
            throw new StayFinderException(ErrorType.DB_ERROR);
        }
    }

    @Transactional
    public void termsModify(TermsDto termsDto) {

    }
}
