package com.vacation.platform.stayfinder.user.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@Getter
@AllArgsConstructor
public enum Gender {
    NONE(0, "NONE", "미선택"),
    MALE(1, "MALE", "남자"),
    FEMALE(2, "FEMALE", "여자");

    private final int code;
    private final String name;
    private final String desc;

    public static Gender getCode(String desc) throws Exception {
        return Arrays.stream(Gender.values()).filter(x ->
            x.getDesc().equals(desc)).findFirst().orElseThrow(Exception::new);
    }

}