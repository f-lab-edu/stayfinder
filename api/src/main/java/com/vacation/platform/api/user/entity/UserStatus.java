package com.vacation.platform.api.user.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum UserStatus {
    UNREGISTERED(0, "회원 탈퇴"),
    REGISTERED(1, "회원 활성화");

    private final int code;
    private final String desc;

}
