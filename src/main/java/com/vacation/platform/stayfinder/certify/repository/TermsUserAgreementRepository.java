package com.vacation.platform.stayfinder.certify.repository;

import com.vacation.platform.stayfinder.certify.entity.TermsUserAgreement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TermsUserAgreementRepository extends JpaRepository<TermsUserAgreement, Long> {
}
