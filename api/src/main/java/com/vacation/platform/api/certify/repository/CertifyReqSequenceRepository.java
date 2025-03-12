package com.vacation.platform.api.certify.repository;

import com.vacation.platform.api.certify.entity.CertifyReqSequence;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CertifyReqSequenceRepository extends JpaRepository<CertifyReqSequence, String> {

    @Modifying
    @Query(value = "UPDATE certify_req_sequence SET next_val = next_val + 1 WHERE sequence_name = :sequenceName", nativeQuery = true)
    void incrementSequence(@Param("sequenceName") String sequenceName);

    @Query(value = "SELECT next_val FROM certify_req_sequence WHERE sequence_name = :sequenceName", nativeQuery = true)
    Long getNextSequenceValue(@Param("sequenceName") String sequenceName);

}
