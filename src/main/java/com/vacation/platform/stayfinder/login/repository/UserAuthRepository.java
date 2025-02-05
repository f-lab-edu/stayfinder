package com.vacation.platform.stayfinder.login.repository;

import com.vacation.platform.stayfinder.login.entity.UserAuth;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserAuthRepository extends JpaRepository<UserAuth, Long> {

    Optional<UserAuth> findByUserId(Long userId);

}