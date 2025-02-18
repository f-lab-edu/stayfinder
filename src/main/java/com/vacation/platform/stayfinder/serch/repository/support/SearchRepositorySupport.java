package com.vacation.platform.stayfinder.serch.repository.support;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.vacation.platform.stayfinder.serch.dto.SearchResponseDTO;
import com.vacation.platform.stayfinder.terms.entity.Terms;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class SearchRepositorySupport extends QuerydslRepositorySupport {

    private final JPAQueryFactory queryFactory;

    public SearchRepositorySupport(JPAQueryFactory queryFactory){
        super(Terms.class);
        this.queryFactory = queryFactory;
    }

    public List<SearchResponseDTO> searchAccommodation(String query) {
        return null;
    }

}
