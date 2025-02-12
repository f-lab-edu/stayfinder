package com.vacation.platform.stayfinder.terms.service.serviceImpl;

import com.vacation.platform.stayfinder.common.EntityToDtoConverter;
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
import com.vacation.platform.stayfinder.util.StayFinderResponseDTO;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
@Slf4j
@AllArgsConstructor
public class TermsServiceImpl implements TermsService {

    private final TermsRepository termsRepository;

    private final TermsSubRepository termsSubRepository;

    private final TermsRepositorySupport termsRepositorySupport;

    @Override
    public StayFinderResponseDTO<List<TermsDto.MainResponseDto>> getTermsMain() {

        List<Terms> termsList = termsRepositorySupport.selectTermsMain();

        if(termsList.isEmpty()) {
            throw new StayFinderException(
                    ErrorType.TERMS_NOT_FOUND,
                    Map.of("termsList", termsList),
                    log::error);
        }


        List<TermsDto.MainResponseDto> result = new ArrayList<>(20);
        try {

            for(Terms t : termsList) {
                TermsDto.MainResponseDto  mainResponseDto = EntityToDtoConverter.convertEntityToDto(t, TermsDto.MainResponseDto.class);
                result.add(mainResponseDto);
            }

        } catch (Exception e) {
            log.info("{}", e.getMessage());
            throw new StayFinderException(ErrorType.DB_ERROR,
                    Map.of("termsList", termsList),
                    log::error,
                    e
            );
        }


        return StayFinderResponseDTO.success(result);
    }

    @Override
    public StayFinderResponseDTO<List<TermsDto.SubResponseDto>> getTermsSub(TermsDto termsDto) {
        List<TermsSub> termsSubList = termsRepositorySupport.selectTermsSub(termsDto);

        if(termsSubList.isEmpty()) {
            throw new StayFinderException(
                    ErrorType.TERMS_NOT_FOUND,
                    Map.of("termsSubList", termsSubList),
                    log::error);
        }

        List<TermsDto.SubResponseDto> result = new ArrayList<>(20);

        try {

            for(TermsSub ts : termsSubList) {
                TermsDto.SubResponseDto subResponseDto = EntityToDtoConverter.convertEntityToDto(ts, TermsDto.SubResponseDto.class);
                result.add(subResponseDto);
            }

        } catch (Exception e) {
            log.info("{}", e.getMessage());
            throw new StayFinderException(ErrorType.DB_ERROR,
                    Map.of("termsSubList", termsSubList),
                    log::error,
                    e
            );
        }

        return StayFinderResponseDTO.success(result);
    }

    @Override
    @Transactional
    public void registerTerms(TermsDto termsDto) {
        Terms terms = termsRepository.findByTermsMainTitleAndIsTermsRequired(termsDto.getMainTitle(), termsDto.getIsCompulsion()).orElse(null);

        if(!termsDto.getIsCompulsion() && terms != null) {
            throw new StayFinderException(
                    ErrorType.DUPLICATE_TERMS_TITLE,
                    Map.of("terms", terms),
                    log::error);
        }

        try {
            if(terms != null) {
                termsUpdate(termsDto, terms);
            }
            termsSave(termsDto);
        } catch (StayFinderException st) {
            throw new StayFinderException(
                    st.getErrorType(),
                    Map.of("terms", Objects.requireNonNull(terms)),
                    log::error,
                    st);
        } catch (Exception e) {
            throw new StayFinderException(
                    ErrorType.DB_ERROR,
                    Map.of("terms", Objects.requireNonNull(terms)),
                    log::error,
                    e);
        }
    }

    @Transactional
    public void termsUpdate(TermsDto termsDto, Terms reqTerms){

        TermsSub sub = termsSubRepository.findByTermsIdOrderByModifyAtDesc(
                reqTerms.getTermsId())
                .orElseThrow(() -> new StayFinderException(ErrorType.DB_ERROR,
                        Map.of("", reqTerms),
                        log::error));

        reqTerms.setIsTermsRequired(termsDto.getIsCompulsion());
        reqTerms.setTermsMainTitle(termsDto.getMainTitle());

        sub.setVersion(sub.getVersion());
        sub.setTermsDetailsTitle(termsDto.getSubTitle());
        sub.setTermsDetailsContent(termsDto.getDetailContent());
        sub.setIsActive(false);
    }

    @Transactional
    protected void termsSave(TermsDto termsDto){
        Terms terms = new Terms();

        terms.setTermsMainTitle(termsDto.getMainTitle());
        terms.setIsTermsRequired(termsDto.getIsCompulsion());
        terms.setSortSeq(termsDto.getSortSeq());

        termsRepository.save(terms);

        Terms term = termsRepository.findByTermsMainTitle(
                        termsDto.getMainTitle())
                .orElseThrow(() -> new StayFinderException(ErrorType.TERMS_NOT_FOUND,
                        Map.of("terms", terms),
                        log::error));

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
