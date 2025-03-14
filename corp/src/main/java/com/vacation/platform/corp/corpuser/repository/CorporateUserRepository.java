package com.vacation.platform.corp.corpuser.repository;

import com.vacation.platform.corp.corpuser.entity.CorporateUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CorporateUserRepository extends JpaRepository<CorporateUser, Long> {

	Optional<CorporateUser> findByEmail(String email);

}
