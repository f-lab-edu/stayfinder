package com.vacation.platform.stayfinder.corpuser.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
@AllArgsConstructor
public enum RequestStatus {
    PENDING(1, "신청중"),
    APPROVED(2, "승인"),
    REJECTED(3, "거절");

    private int code;
    private String desc;
}
