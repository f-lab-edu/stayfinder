package com.vacation.platform.stayfinder.corpuser.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

@Getter
@RequiredArgsConstructor
@AllArgsConstructor
public enum BusinessCategory {
    HOTEL(0, "HOTEL"),
    MOTEL(1, "MOTEL"),
    RESORT(2, "RESORT"),
    PENSION(3, "PENSION");

    private Integer code;
    private String  description;

    public static BusinessCategory getByDesc(String code) throws Exception {
        return Arrays.stream(BusinessCategory.values())
                .filter(e -> e.getDescription().equals(code))
                .findFirst().orElseThrow(Exception::new);
    }

}
