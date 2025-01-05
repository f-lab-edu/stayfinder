package com.vacation.platform.stayfinder.certify.repository;

import com.vacation.platform.stayfinder.certify.entity.PhoneCertifyReq;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PhoneCertifyRepository extends JpaRepository<PhoneCertifyReq, Long> {
}
