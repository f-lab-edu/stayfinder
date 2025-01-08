package com.vacation.platform.stayfinder.terms.repository;

import com.vacation.platform.stayfinder.terms.entity.Terms;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TermsRepository extends JpaRepository<Terms, Long> {

    List<Terms> findAllByIsActive(boolean isActive);
    Terms findByTermsMainTileAndIsActive(String mainTitle, boolean isActive);

}
