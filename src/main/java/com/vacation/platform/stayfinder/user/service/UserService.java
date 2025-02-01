package com.vacation.platform.stayfinder.user.service;


import com.vacation.platform.stayfinder.user.dto.LoginDTO;
import com.vacation.platform.stayfinder.user.dto.UserDTO;
import com.vacation.platform.stayfinder.util.StayFinderResponseDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public interface  UserService {

    void saveUser(UserDTO.saveDTO user);

    ResponseEntity<StayFinderResponseDTO<?>> login(LoginDTO loginDTO);

//    ResponseEntity<StayFinderResponseDTO<?>> modifyUser(UserDTO.saveDTO modifyDTO);

}
