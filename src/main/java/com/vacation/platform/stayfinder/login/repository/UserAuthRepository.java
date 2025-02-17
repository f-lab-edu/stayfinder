package com.vacation.platform.stayfinder.login.repository;

import com.vacation.platform.stayfinder.login.entity.UserAuth;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserAuthRepository extends JpaRepository<UserAuth, Long> {

    Optional<UserAuth> findByUserId(Long userId);

    @Query(value = "SELECT * FROM user_auth u WHERE u.refresh_token = :refreshToken", nativeQuery = true)
    Optional<UserAuth> findByRefreshToken(@Param("refreshToken") String refreshToken);

}