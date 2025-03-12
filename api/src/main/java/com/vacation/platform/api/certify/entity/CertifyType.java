package com.vacation.platform.api.certify.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CertifyType {
    EMAIL(0, "eMail"),
    PHONE(1, "phone");

    private final int code;
    private final String value;

}
