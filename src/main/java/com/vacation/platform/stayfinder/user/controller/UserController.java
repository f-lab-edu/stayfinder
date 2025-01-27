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
                    "null pointer",
                    x -> log.error("{}", ErrorType.DTO_NOT_FOUND.getInternalMessage()),
                    null);

        userService.saveUser(users);

        return ResponseEntity.ok(StayFinderResponseDTO.success());
    }

}
