package com.vacation.platform.stayfinder.user.controller;

import com.vacation.platform.stayfinder.common.ErrorType;
import com.vacation.platform.stayfinder.common.StayFinderException;
import com.vacation.platform.stayfinder.user.dto.UserDTO;
import com.vacation.platform.stayfinder.user.service.UserService;
import com.vacation.platform.stayfinder.util.StayFinderResponseDTO;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;


@RestController
@RequestMapping("/api/v1/users")
@Slf4j
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    //user 생성
    @PostMapping("/create")
    public StayFinderResponseDTO<?> createUser(@Valid @RequestBody UserDTO.saveDTO users) {
        if(!users.getPassword().equals(users.getPasswordCheck())) {
            throw new StayFinderException(ErrorType.USER_PASSWORD_NOT_MATCHED,
                    Map.of("users", users),
                    log::error);
        }

        userService.saveUser(users);

        return StayFinderResponseDTO.success();
    }

}
