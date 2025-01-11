package com.vacation.platform.stayfinder.certify.repository;

import com.vacation.platform.stayfinder.certify.entity.CertifyReq;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CertifyRepository extends JpaRepository<CertifyReq, Long> {



}
