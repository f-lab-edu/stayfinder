package com.vacation.platform.stayfinder.corpuser.repository;

import com.vacation.platform.stayfinder.corpuser.entity.CorpUser;
import jdk.jfr.Registered;
import org.springframework.data.repository.CrudRepository;

@Registered
public interface CorpUserRepository extends CrudRepository<CorpUser,String> {
}
