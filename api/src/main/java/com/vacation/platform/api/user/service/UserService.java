package com.vacation.platform.api.user.service;


import com.vacation.platform.api.user.dto.UserDTO;
import org.springframework.stereotype.Service;

@Service
public interface  UserService {

    void saveUser(UserDTO.saveDTO user);

}
