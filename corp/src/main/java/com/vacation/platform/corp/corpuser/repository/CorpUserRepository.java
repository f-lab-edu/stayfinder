package com.vacation.platform.corp.corpuser.repository;

import com.vacation.platform.corp.corpuser.entity.CorpStatus;
import com.vacation.platform.corp.corpuser.entity.CorpUser;
import jdk.jfr.Registered;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

@Registered
public interface CorpUserRepository extends CrudRepository<CorpUser,String> {

	@Query("SELECT c FROM CorpUser c WHERE c.businessLicense = :businessLicense AND c.corpStatus = :corpStatus")
	Optional<CorpUser> findByBusinessLicense(String businessLicense, CorpStatus corpStatus);

}
