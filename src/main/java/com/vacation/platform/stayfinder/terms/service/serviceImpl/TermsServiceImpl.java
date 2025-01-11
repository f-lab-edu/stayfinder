package com.vacation.platform.stayfinder.terms.service.serviceImpl;

import com.vacation.platform.stayfinder.terms.dto.TermsDto;
import com.vacation.platform.stayfinder.terms.entity.Terms;
import com.vacation.platform.stayfinder.terms.entity.TermsRequired;
import com.vacation.platform.stayfinder.terms.entity.TermsSub;
import com.vacation.platform.stayfinder.terms.repository.TermsRepository;
import com.vacation.platform.stayfinder.terms.repository.TermsSubRepository;
import com.vacation.platform.stayfinder.certify.repository.TermsUserAgreementRepository;
import com.vacation.platform.stayfinder.terms.service.TermsService;
import com.vacation.platform.stayfinder.util.ResponseCode;
import com.vacation.platform.stayfinder.util.Result;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

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

        List<Terms> termsList = termsRepository.findAllByIsActive(true);

        if(termsList.size() > 0) {
            return Result.success(termsList);
        }

        return Result.success();
    }

    @Override
    public Result<List<TermsSub>> getTermsSub(TermsDto termsDto) {
        return null;
    }

    // 약관 저장 하는것부터 처리해야함
    // 필요한것은 약관 메인 타이틀, 약관 서브 타이틀
    // 약관 내용
    // 약관 버전

    @Override
    @Transactional
    public Result<?> registerTerms(TermsDto termsDto) {
        Terms terms = termsRepository.findByTermsMainTileAndIsActive(termsDto.getMainTitle(), true);

        if(!termsDto.isCompulsion() && terms != null) {
            return Result.fail(ResponseCode.SUCCESS, "해당 제목은 내용이 존재합니다.", termsDto.getMainTitle());
        } else if(termsDto.isCompulsion() && terms != null) {
            // 수정 서비스로 토스
        }

        return termsSave(termsDto);
    }


    @Transactional
    private Result<?> termsSave(TermsDto termsDto) {
        try {
            Terms terms = new Terms();

            terms.setTermsMainTile(termsDto.getMainTitle());
            terms.setTermsRequired(TermsRequired.getIsRequired(termsDto.getIsRequired()));
            terms.setActive(true);

            termsRepository.save(terms);

            Terms newTerms = termsRepository.findByTermsMainTileAndIsActive(terms.getTermsMainTile(), true);

            TermsSub termsSub = new TermsSub();
            termsSub.setTermsMainId(newTerms);
            termsSub.setTermsDetailsTitle(termsDto.getSubTitle());
            termsSub.setTermsDetailsContent(termsDto.getDetailContent());
            termsSub.setVersion(1);
            termsSub.setActive(true);

            termsSubRepository.save(termsSub);

            return Result.success();
        } catch (Exception e) {
            log.error("termsSave error {}", e.getMessage());
            return Result.fail(ResponseCode.INTERNAL_SERVER_ERROR, ResponseCode.INTERNAL_SERVER_ERROR.getCustomMessage());
        }

    }
}
