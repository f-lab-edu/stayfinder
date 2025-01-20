package com.vacation.platform.stayfinder.terms.repository.support;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.vacation.platform.stayfinder.terms.entity.Terms;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

@Repository
public class TermsRepositorySupport extends QuerydslRepositorySupport {

    private final JPAQueryFactory queryFactory;

    public TermsRepositorySupport(JPAQueryFactory queryFactory){
        super(Terms.class);
        this.queryFactory = queryFactory;
    }



}
