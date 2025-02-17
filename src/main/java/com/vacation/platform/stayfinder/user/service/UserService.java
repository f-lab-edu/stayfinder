package com.vacation.platform.stayfinder.user.service;


import com.vacation.platform.stayfinder.user.dto.UserDTO;
import org.springframework.stereotype.Service;

@Service
public interface  UserService {

    void saveUser(UserDTO.saveDTO user);

}
