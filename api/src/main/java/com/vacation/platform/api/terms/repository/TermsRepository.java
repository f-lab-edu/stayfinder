package com.vacation.platform.api.terms.repository;

import com.vacation.platform.api.terms.entity.Terms;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TermsRepository extends JpaRepository<Terms, Long> {

    @Query(value = "SELECT * FROM terms WHERE terms_main_title = :mainTitle ORDER BY created_at DESC LIMIT 1", nativeQuery = true)
    Optional<Terms> findByTermsMainTitle(@Param("mainTitle") String mainTitle);

    @Query(value = "SELECT * FROM terms WHERE terms_main_title = :mainTitle AND is_terms_required = :isRequired ORDER BY created_at DESC LIMIT 1", nativeQuery = true)
    Optional<Terms> findByTermsMainTitleAndIsTermsRequired(@Param("mainTitle") String mainTitle, @Param("isRequired") Boolean isRequired);

}
