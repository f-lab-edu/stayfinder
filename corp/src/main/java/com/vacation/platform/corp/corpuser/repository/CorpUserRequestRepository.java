package com.vacation.platform.corp.corpuser.repository;

import com.vacation.platform.corp.corpuser.entity.CorpUserRequest;
import com.vacation.platform.corp.corpuser.entity.RequestStatus;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface CorpUserRequestRepository extends JpaRepository<CorpUserRequest, Long> {

    @Query(value = "SELECT r FROM CorpUserRequest r WHERE r.businessLicense = :businessLicense AND r.status <> :status")
    Optional<CorpUserRequest> findByBusinessLicense(@Param("businessLicense") String businessLicense,  @Param("status") RequestStatus status);

    @Query("SELECT r FROM CorpUserRequest r WHERE r.businessLicense = :businessLicense")
    Optional<CorpUserRequest> findByBusinessLicense(@Param("businessLicense") String businessLicense);

    @Query("SELECT DISTINCT c FROM CorpUserRequest c LEFT JOIN FETCH c.businessLicenseFiles WHERE (c.createdAt >= :startDate) AND (c.createdAt <= :endDate) AND c.status = :requestStatus")
    List<CorpUserRequest> findByCorpUserRequests(@Param("startDate")LocalDateTime startDate, @Param("endDate") LocalDateTime endDate, @Param("requestStatus") RequestStatus requestStatus);

}
