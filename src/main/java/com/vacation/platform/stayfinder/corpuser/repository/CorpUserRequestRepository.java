package com.vacation.platform.stayfinder.corpuser.repository;

import com.vacation.platform.stayfinder.corpuser.entity.CorpUserRequest;
import com.vacation.platform.stayfinder.corpuser.entity.RequestStatus;
import io.lettuce.core.dynamic.annotation.Param;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CorpUserRequestRepository extends JpaRepository<CorpUserRequest, Long> {

    @Query(value = "SELECT r FROM CorpUserRequest r WHERE r.businessLicense = :businessLicense AND r.status != :status")
    Optional<CorpUserRequest> findByBusinessLicense(@NotBlank(message = "사업자 번호는 필수 입니다.") @Param("businessLicense") String businessLicense,  @Param("status") RequestStatus status);
}
