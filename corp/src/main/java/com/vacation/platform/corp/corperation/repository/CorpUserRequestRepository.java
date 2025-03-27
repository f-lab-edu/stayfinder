package com.vacation.platform.corp.corperation.repository;

import com.vacation.platform.corp.corperation.entity.CorporationRequest;
import com.vacation.platform.corp.corperation.entity.room.RequestStatus;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface CorpUserRequestRepository extends JpaRepository<CorporationRequest, Long> {

    @Query(value = "SELECT r FROM CorporationRequest r WHERE r.businessLicense = :businessLicense AND r.status <> :status")
    Optional<CorporationRequest> findByBusinessLicense(@Param("businessLicense") String businessLicense, @Param("status") RequestStatus status);

    @Query("SELECT r FROM CorporationRequest r WHERE r.businessLicense = :businessLicense")
    Optional<CorporationRequest> findByBusinessLicense(@Param("businessLicense") String businessLicense);

    @Query("SELECT DISTINCT c FROM CorporationRequest c LEFT JOIN FETCH c.businessLicenseFiles WHERE (c.createdAt >= :startDate) AND (c.createdAt <= :endDate) AND c.status = :requestStatus")
    List<CorporationRequest> findByCorpUserRequests(@Param("startDate")LocalDateTime startDate, @Param("endDate") LocalDateTime endDate, @Param("requestStatus") RequestStatus requestStatus);

}
