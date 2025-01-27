package com.vacation.platform.stayfinder.terms.repository;

import com.vacation.platform.stayfinder.terms.entity.Terms;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TermsRepository extends JpaRepository<Terms, Long> {

    @Query("SELECT t FROM Terms t WHERE t.termsMainTitle = :mainTitle ORDER BY t.createAt DESC")
    Optional<Terms> findByTermsMainTitle(@Param("mainTitle") String mainTitle);

    @Query(value = "SELECT * FROM terms t WHERE t.terms_main_title = :mainTitle AND t.is_terms_required = :isRequired ORDER BY t.create_at DESC LIMIT 1", nativeQuery = true)
    Optional<Terms> findByTermsMainTitleAndIsTermsRequired(@Param("mainTitle") String mainTitle, @Param("isRequired") Boolean isRequired);


//    @Query(value = "SELECT t FROM Terms t WHERE t.termsId IN :termsMainIds")
//    List<Terms> findByTermsIdIn(@Param("termsMainIds") List<Long> termsMainIds);

}
