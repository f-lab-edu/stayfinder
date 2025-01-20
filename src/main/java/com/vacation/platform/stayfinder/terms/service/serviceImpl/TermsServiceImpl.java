package com.vacation.platform.stayfinder.terms.service.serviceImpl;

import com.vacation.platform.stayfinder.common.ErrorType;
import com.vacation.platform.stayfinder.common.StayFinderException;
import com.vacation.platform.stayfinder.terms.dto.TermsDto;
import com.vacation.platform.stayfinder.terms.entity.Terms;
import com.vacation.platform.stayfinder.terms.entity.TermsSub;
import com.vacation.platform.stayfinder.terms.entity.TermsSubId;
import com.vacation.platform.stayfinder.terms.repository.TermsRepository;
import com.vacation.platform.stayfinder.terms.repository.TermsSubRepository;
import com.vacation.platform.stayfinder.terms.service.TermsService;
import com.vacation.platform.stayfinder.util.Result;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@AllArgsConstructor
public class TermsServiceImpl implements TermsService {

    private final TermsRepository termsRepository;

    private final TermsSubRepository termsSubRepository;

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
        Optional<Terms> terms = termsRepository.findByTermsMainTitleAndIsTermsRequired(termsDto.getMainTitle(), termsDto.isRequired());

        if(!termsDto.isCompulsion() && terms.isPresent()) {
            throw new StayFinderException(ErrorType.DUPLICATE_TERMS_TITLE);
        } else if(termsDto.isCompulsion() && terms.isPresent()) {
            termsUpdate(termsDto, terms.get());
        }

        termsSave(termsDto);
    }


    @Transactional
    private void termsSave(TermsDto termsDto) {
        Terms terms = new Terms();

        terms.setTermsMainTitle(termsDto.getMainTitle());
        terms.setIsTermsRequired(termsDto.isRequired());

        try {
            termsRepository.save(terms);

             Optional<Terms> termsOptional = termsRepository.findFirstByTermsMainTitleOrderByCreateAtDesc(termsDto.getMainTitle());

            if(termsOptional.isPresent()) {
                Terms termsResult = termsOptional.get();
                TermsSub termsSub = new TermsSub();
                TermsSubId id = new TermsSubId();

                id.setTermsId(termsResult.getTermsId());

                termsSub.setTermsDetailsTitle(termsDto.getSubTitle());
                termsSub.setTermsDetailsContent(termsDto.getDetailContent());
                termsSub.setIsActive(true);
                termsSub.setTerms(termsResult);
                termsSub.setVersion(id.getTermsId());
                termsSub.setTermsId(id.getTermsId());
                termsSubRepository.save(termsSub);
            }


        } catch (Exception e) {
            throw new StayFinderException(ErrorType.DB_ERROR);
        }
    }

    @Transactional
    public void termsUpdate(TermsDto termsDto, Terms reqTerms) {

        Optional<TermsSub> termsSub;
        try {
            termsSub = termsSubRepository.findByTermsIdOrderByModifyAtDesc(reqTerms.getTermsId());
        } catch (Exception e) {
            throw new StayFinderException(ErrorType.DB_ERROR);
        }

        if(termsSub.isPresent()) {
            reqTerms.setIsTermsRequired(termsDto.isRequired());
            reqTerms.setTermsMainTitle(termsDto.getMainTitle());
            TermsSub sub = termsSub.get();

            sub.setVersion(sub.getVersion());
            sub.setTermsDetailsTitle(termsDto.getSubTitle());
            sub.setTermsDetailsContent(termsDto.getDetailContent());
            sub.setIsActive(false);
        }

    }
}
