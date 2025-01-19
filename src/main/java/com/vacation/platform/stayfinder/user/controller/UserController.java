package com.vacation.platform.stayfinder.user.controller;

import com.vacation.platform.stayfinder.common.ErrorType;
import com.vacation.platform.stayfinder.common.StayFinderException;
import com.vacation.platform.stayfinder.user.entity.User;
import com.vacation.platform.stayfinder.user.service.UserService;
import com.vacation.platform.stayfinder.util.Result;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    //user 생성
    @PostMapping("/create")
    public Result<?> createUser(@Valid @RequestBody User users) {
        if(users == null) throw new StayFinderException(ErrorType.SYSTEM_ERROR);

        userService.saveUser(users);

        return Result.success();
    }

}
