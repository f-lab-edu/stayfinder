package com.vacation.platform.stayfinder.user.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Role {
    USER,
    CORP_USER,
    ADMIN
}
