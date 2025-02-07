package com.vacation.platform.stayfinder.login.repository;

import com.vacation.platform.stayfinder.login.entity.UserAuthSequence;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserAuthSequenceRepository  extends CrudRepository<UserAuthSequence, Integer> {

    @Modifying
    @Query(value = "UPDATE user_auth_sequence SET next_val = next_val + 1 WHERE sequence_name = :sequenceName", nativeQuery = true)
    void incrementSequence(@Param("sequenceName") String sequenceName);

    @Query(value = "SELECT next_val FROM user_auth_sequence WHERE sequence_name = :sequenceName", nativeQuery = true)
    Long getNextSequenceValue(@Param("sequenceName") String sequenceName);

}
