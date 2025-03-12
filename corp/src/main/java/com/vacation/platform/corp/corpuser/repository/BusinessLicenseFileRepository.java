package com.vacation.platform.corp.corpuser.repository;

import com.vacation.platform.corp.corpuser.entity.BusinessLicenseFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BusinessLicenseFileRepository extends JpaRepository<BusinessLicenseFile, Integer> {
}
