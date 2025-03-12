package com.vacation.platform.api.certify.repository;

import com.vacation.platform.api.certify.entity.TermsUserAgreement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TermsUserAgreementRepository extends JpaRepository<TermsUserAgreement, Long> {
}
