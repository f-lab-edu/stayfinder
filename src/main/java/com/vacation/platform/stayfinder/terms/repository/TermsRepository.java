package com.vacation.platform.stayfinder.terms.repository;

import com.vacation.platform.stayfinder.terms.entity.Terms;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TermsRepository extends JpaRepository<Terms, Long> {
    Optional<Terms> findFirstByTermsMainTitleOrderByCreateAtDesc(String mainTitle);

    Optional<Terms> findByTermsMainTitleAndIsTermsRequired(String mainTitle, Boolean isRequired);
}
