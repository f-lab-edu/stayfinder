package com.vacation.platform.corp.corpuser.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum CorpStatus {
    UNREGISTERED(0, "회원 탈퇴"),
    REGISTERED(1, "회원 활성화");

    private final int code;
    private final String desc;
}