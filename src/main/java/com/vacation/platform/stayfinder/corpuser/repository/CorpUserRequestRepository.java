package com.vacation.platform.stayfinder.corpuser.repository;

import com.vacation.platform.stayfinder.corpuser.entity.CorpUserRequest;
import jdk.jfr.Registered;
import org.springframework.data.repository.Repository;

@Registered
public interface CorpUserRequestRepository extends Repository<CorpUserRequest, Long> {

}
