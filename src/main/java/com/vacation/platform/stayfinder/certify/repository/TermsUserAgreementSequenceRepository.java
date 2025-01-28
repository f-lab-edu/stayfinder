package com.vacation.platform.stayfinder.certify.repository;

import com.vacation.platform.stayfinder.certify.entity.TermsUserAgreementSequence;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface TermsUserAgreementSequenceRepository extends JpaRepository<TermsUserAgreementSequence, Long> {

    @Modifying
    @Query(value = "UPDATE terms_user_agreement_sequence SET next_val = next_val + 1 WHERE sequence_name = :sequenceName", nativeQuery = true)
    void incrementSequence(@Param("sequenceName") String sequenceName);

    @Query(value = "SELECT next_val FROM terms_user_agreement_sequence WHERE sequence_name = :sequenceName", nativeQuery = true)
    Long getNextSequenceValue(@Param("sequenceName") String sequenceName);

}
