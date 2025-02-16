package com.vacation.platform.stayfinder.corpuser.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum RequestStatus {
    PENDING,
    APPROVED,
    REJECTED
}
