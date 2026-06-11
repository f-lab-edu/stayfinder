package com.vacation.platform.corp.serch.repository.support;

import com.vacation.platform.api.terms.entity.Terms;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public class SearchRepositorySupport extends QuerydslRepositorySupport {

    private final EntityManager entityManager;

    public SearchRepositorySupport(EntityManager entityManager) {
        super(Terms.class);
        this.entityManager = entityManager;
    }

    public List<?> searchAccommodation(String location, LocalDate startDate, LocalDate endDate, int capacity) {
        String sql = "WITH ranked_products AS ( " +
                "    SELECT " +
                "        c.business_title, " +
                "        c.business_category, " +
                "        c.business_address, " +
                "        r.name, " +
                "        r.price, " +
                "        p.available_date, " +
                "        ROW_NUMBER() OVER (PARTITION BY p.room_id ORDER BY p.available_date) AS rn " +
                "    FROM " +
                "        corp_user c " +
                "    JOIN " +
                "        room r ON r.corp_user_id = c.corp_user_id " +
                "    JOIN " +
                "        products p ON p.room_id = r.room_id " +
                "    WHERE " +
                "        MATCH(c.business_address) AGAINST (? IN BOOLEAN MODE) " +
                "        AND p.available_date BETWEEN ? AND ? " +
                "        AND r.capacity >= ? " +
                ") " +
                "SELECT " +
                "    business_title, " +
                "    business_category, " +
                "    business_address, " +
                "    name, " +
                "    price, " +
                "    available_date " +
                "FROM " +
                "    ranked_products " +
                "WHERE " +
                "    rn = 1 " +  // 첫 번째 예약 날짜만 선택
                "ORDER BY " +
                "    available_date";

        // NativeQuery 실행
        Query nativeQuery = entityManager.createNativeQuery(sql);

        nativeQuery.setParameter(1, location);  // 지역 검색어
        nativeQuery.setParameter(2, startDate);  // 예약 시작일
        nativeQuery.setParameter(3, endDate);  // 예약 종료일
        nativeQuery.setParameter(4, capacity);  // 수용 인원

        return nativeQuery.getResultList();
        }
}
