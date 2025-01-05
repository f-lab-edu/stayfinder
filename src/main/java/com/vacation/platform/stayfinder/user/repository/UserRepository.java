package com.vacation.platform.stayfinder.user.repository;

import com.vacation.platform.stayfinder.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    List<User> findByNickName(String nickName);
}
