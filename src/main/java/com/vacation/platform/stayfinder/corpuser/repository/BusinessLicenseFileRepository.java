package com.vacation.platform.stayfinder.corpuser.repository;

import com.vacation.platform.stayfinder.corpuser.entity.BusinessLicenseFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BusinessLicenseFileRepository extends JpaRepository<BusinessLicenseFile, Integer> {
}
