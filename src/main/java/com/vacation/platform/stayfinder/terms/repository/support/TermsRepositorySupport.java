package com.vacation.platform.stayfinder.terms.repository.support;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.vacation.platform.stayfinder.terms.dto.TermsDto;
import com.vacation.platform.stayfinder.terms.entity.Terms;
import com.vacation.platform.stayfinder.terms.entity.TermsSub;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.vacation.platform.stayfinder.terms.entity.QTerms.terms;
import static com.vacation.platform.stayfinder.terms.entity.QTermsSub.termsSub;

@Repository
public class TermsRepositorySupport extends QuerydslRepositorySupport {

    private final JPAQueryFactory queryFactory;

    public TermsRepositorySupport(JPAQueryFactory queryFactory){
        super(Terms.class);
        this.queryFactory = queryFactory;
    }

    public List<Terms> selectTermsMain() {
        return queryFactory.select(Projections.fields(
                        Terms.class,
                        terms.termsId,
                        terms.termsMainTitle,
                        terms.isTermsRequired
                ))
                .from(terms)
                .innerJoin(termsSub)
                .on(terms.termsId.eq(termsSub.termsId))
                .where(termsSub.isActive)
                .orderBy(terms.sortSeq.asc())
                .groupBy(terms.termsMainTitle, terms.termsId, terms.isTermsRequired)
                .fetch();
    }

    public List<TermsSub> selectTermsSub(TermsDto termsDto) {
        return queryFactory.select(Projections.fields(
                        TermsSub.class,
                        termsSub.termsDetailsTitle,
                        termsSub.termsDetailsContent,
                        termsSub.createAt,
                        termsSub.isActive
                ))
                .from(termsSub)
                .where(termsSub.termsDetailsTitle.eq(termsDto.getSubTitle()))
                .orderBy(termsSub.isActive.desc())
                .orderBy(termsSub.createAt.desc())
                .fetch();
    }


}
