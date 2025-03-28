package com.vacation.platform.corp.corperation.repository;

import com.vacation.platform.corp.corperation.entity.CorpStatus;
import com.vacation.platform.corp.corperation.entity.Corporation;
import jdk.jfr.Registered;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

@Registered
public interface CorporationRepository extends JpaRepository<Corporation,String> {

	@Query("SELECT c FROM Corporation c WHERE c.businessLicense = :businessLicense AND c.corpStatus = :corpStatus")
	Optional<Corporation> findByBusinessLicense(String businessLicense, CorpStatus corpStatus);

	@Query("SELECT c FROM Corporation c WHERE c.corpStatus = :corpStatus")
	List<Corporation> findByCorpStatus(CorpStatus corpStatus);

}
