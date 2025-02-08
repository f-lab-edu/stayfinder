package com.vacation.platform.stayfinder.user.controller;

import com.vacation.platform.stayfinder.common.ErrorType;
import com.vacation.platform.stayfinder.common.StayFinderException;
import com.vacation.platform.stayfinder.user.dto.UserDTO;
import com.vacation.platform.stayfinder.user.service.UserService;
import com.vacation.platform.stayfinder.util.StayFinderResponseDTO;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<StayFinderResponseDTO<?>> createUser(@Valid @RequestBody UserDTO.saveDTO users) {
        if(users == null)
            throw new StayFinderException(ErrorType.DTO_NOT_FOUND,
                    null,
                    x -> log.error("{}", ErrorType.DTO_NOT_FOUND.getInternalMessage()),
                    null);

        if(!users.getPassword().equals(users.getPasswordCheck())) {
            throw new StayFinderException(ErrorType.USER_PASSWORD_NOT_MATCHED,
                    Map.of("users", users),
                    log::error);
        }

        if(users.getEmail() != null) {
            String emailValid = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";

            if(emailValid.matches(users.getEmail())) {
                throw new StayFinderException(ErrorType.USER_EMAIL_NOT_VALID,
                        Map.of("users", users),
                        log::error);
            }
        }

        userService.saveUser(users);

        return ResponseEntity.ok(StayFinderResponseDTO.success());
    }

}
