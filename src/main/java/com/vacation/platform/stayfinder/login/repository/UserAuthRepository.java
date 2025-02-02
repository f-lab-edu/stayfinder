package com.vacation.platform.stayfinder.login.repository;

import com.vacation.platform.stayfinder.login.entity.UserAuth;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserAuthRepository extends JpaRepository<UserAuth, Long> {

}