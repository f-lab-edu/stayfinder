package com.vacation.platform.stayfinder.user.service;


import com.vacation.platform.stayfinder.user.dto.UserDTO;
import com.vacation.platform.stayfinder.user.entity.User;
import com.vacation.platform.stayfinder.util.Result;
import org.springframework.stereotype.Service;

@Service
public interface  UserService {

    void saveUser(User user);
    Result<?> modifyUser(UserDTO.saveDTO modifyDTO);

}
