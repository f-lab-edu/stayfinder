package com.vacation.platform.stayfinder.user.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum UserStatus {
    UNREGISTERED(0, "UNREGISTERED", "회원 탈퇴"),
    REGISTERED(1, "REGISTERED", "회원 활성화");

    private final int code;
    private final String name;
    private final String desc;
}
