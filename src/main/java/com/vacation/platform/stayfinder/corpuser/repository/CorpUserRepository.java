package com.vacation.platform.stayfinder.corpuser.repository;

import com.vacation.platform.stayfinder.corpuser.entity.CorpStatus;
import com.vacation.platform.stayfinder.corpuser.entity.CorpUser;
import jdk.jfr.Registered;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

@Registered
public interface CorpUserRepository extends CrudRepository<CorpUser,String> {

	@Query("SELECT c FROM CorpUser c WHERE c.businessLicense = :businessLicense AND c.corpStatus = :corpStatus")
	Optional<CorpUser> findByBusinessLicense(String businessLicense, CorpStatus corpStatus);

}
