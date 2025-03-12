package com.vacation.platform.api.certify.repository;

import com.vacation.platform.api.certify.entity.CertifyReq;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CertifyRepository extends JpaRepository<CertifyReq, Long> {



}
