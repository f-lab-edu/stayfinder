package com.vacation.platform.stayfinder.serch.repository.support;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.vacation.platform.stayfinder.serch.dto.SearchResponseDTO;
import com.vacation.platform.stayfinder.terms.entity.Terms;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.vacation.platform.stayfinder.corpuser.entity.QCorpUser.corpUser;

@Repository
public class SearchRepositorySupport extends QuerydslRepositorySupport {

    private final JPAQueryFactory queryFactory;

    public SearchRepositorySupport(JPAQueryFactory queryFactory){
        super(Terms.class);
        this.queryFactory = queryFactory;
    }

    public List<SearchResponseDTO> searchAccommodation(String query) {
        return queryFactory.select(Projections.fields(
                        SearchResponseDTO.class,
                        corpUser.businessTitle,
                        corpUser.businessCategory,
                        corpUser.businessAddress
                ))
                .from(corpUser)
                .where(corpUser.businessTitle.eq(query))
                .orderBy(corpUser.businessTitle.asc())
                .fetch();
    }

}
