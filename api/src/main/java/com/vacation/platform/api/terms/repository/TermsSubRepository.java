package com.vacation.platform.api.terms.repository;

import com.vacation.platform.api.terms.entity.TermsSub;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TermsSubRepository extends JpaRepository<TermsSub, Long> {
    Optional<TermsSub> findByTermsIdOrderByModifyAtDesc(Long id);
}
