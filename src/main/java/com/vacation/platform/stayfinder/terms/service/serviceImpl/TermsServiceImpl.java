package com.vacation.platform.stayfinder.terms.service.serviceImpl;

import com.vacation.platform.stayfinder.common.ErrorType;
import com.vacation.platform.stayfinder.common.StayFinderException;
import com.vacation.platform.stayfinder.terms.dto.TermsDto;
import com.vacation.platform.stayfinder.terms.entity.Terms;
import com.vacation.platform.stayfinder.terms.entity.TermsSub;
import com.vacation.platform.stayfinder.terms.entity.TermsSubId;
import com.vacation.platform.stayfinder.terms.repository.TermsRepository;
import com.vacation.platform.stayfinder.terms.repository.TermsSubRepository;
import com.vacation.platform.stayfinder.terms.repository.support.TermsRepositorySupport;
import com.vacation.platform.stayfinder.terms.service.TermsService;
import com.vacation.platform.stayfinder.util.Result;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@AllArgsConstructor
public class TermsServiceImpl implements TermsService {

    private final TermsRepository termsRepository;

    private final TermsSubRepository termsSubRepository;

    private final TermsRepositorySupport termsRepositorySupport;

    @Override
    public Result<List<Terms>> getTermsMain() {

        List<Terms> termsList = termsRepositorySupport.selectTermsMain();

        if(termsList.size() < 1) {
            throw new StayFinderException(
                    ErrorType.TERMS_NOT_FOUND,
                    termsList,
                    x -> log.error("{}", ErrorType.TERMS_NOT_FOUND.getInternalMessage()),
                    null);
        }
        return Result.success(termsList);
    }

    @Override
    public Result<List<TermsSub>> getTermsSub(TermsDto termsDto) {
        List<TermsSub> termsSubList = termsRepositorySupport.selectTermsSub(termsDto);

        if(termsSubList.size() < 1) {
            throw new StayFinderException(
                    ErrorType.TERMS_NOT_FOUND,
                    termsSubList,
                    x -> log.error("{}", ErrorType.TERMS_NOT_FOUND.getInternalMessage()),
                    null);
        }

        return Result.success(termsSubList);
    }

    @Override
    @Transactional
    public void registerTerms(TermsDto termsDto) {
        Terms terms = termsRepository.findByTermsMainTitleAndIsTermsRequired(termsDto.getMainTitle(), termsDto.getIsCompulsion()).orElse(null);

        try {
            if(!termsDto.getIsCompulsion() && terms != null) {
                throw new StayFinderException(
                        ErrorType.DUPLICATE_TERMS_TITLE,
                        terms,
                        x -> log.error("{}", ErrorType.DUPLICATE_TERMS_TITLE.getInternalMessage()),
                        null);
            } else if(termsDto.getIsCompulsion() && terms != null) {
                termsUpdate(termsDto, terms);
            }
            termsSave(termsDto);
        } catch (Exception e) {
            throw new StayFinderException(
                    ErrorType.DB_ERROR,
                    terms,
                    x -> log.error("{}", (Object) e.getStackTrace()),
                    null);
        }
    }

    @Transactional
    public void termsUpdate(TermsDto termsDto, Terms reqTerms) throws Exception {

        TermsSub sub = termsSubRepository.findByTermsIdOrderByModifyAtDesc(
                reqTerms.getTermsId())
                .orElseThrow(() -> new StayFinderException(ErrorType.DB_ERROR,
                        null,
                        x -> log.error("{}", ErrorType.DB_ERROR.getInternalMessage()),
                        null));

        reqTerms.setIsTermsRequired(termsDto.getIsCompulsion());
        reqTerms.setTermsMainTitle(termsDto.getMainTitle());

        sub.setVersion(sub.getVersion());
        sub.setTermsDetailsTitle(termsDto.getSubTitle());
        sub.setTermsDetailsContent(termsDto.getDetailContent());
        sub.setIsActive(false);
    }

    @Transactional
    private void termsSave(TermsDto termsDto) throws Exception {
        Terms terms = new Terms();

        terms.setTermsMainTitle(termsDto.getMainTitle());
        terms.setIsTermsRequired(termsDto.getIsCompulsion());
        terms.setSortSeq(termsDto.getSortSeq());

        termsRepository.save(terms);

        Terms term = termsRepository.findFirstByTermsMainTitleOrderByCreateAtDesc(
                        termsDto.getMainTitle())
                .orElseThrow(() -> new StayFinderException(ErrorType.TERMS_NOT_FOUND,
                        terms,
                        x -> log.error("{}", ErrorType.TERMS_NOT_FOUND.getInternalMessage()),
                        null));

        TermsSub termsSub = new TermsSub();
        TermsSubId id = new TermsSubId();

        id.setTermsId(term.getTermsId());

        termsSub.setTermsDetailsTitle(termsDto.getSubTitle());
        termsSub.setTermsDetailsContent(termsDto.getDetailContent());
        termsSub.setIsActive(true);
        termsSub.setTerms(term);
        termsSub.setVersion(id.getTermsId());
        termsSub.setTermsId(id.getTermsId());
        termsSubRepository.save(termsSub);
    }
}
