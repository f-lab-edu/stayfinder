package com.vacation.platform.stayfinder.user.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Role {
    ROLE_USER,
    ROLE_CORP_USER,
    ROLE_ADMIN
}
