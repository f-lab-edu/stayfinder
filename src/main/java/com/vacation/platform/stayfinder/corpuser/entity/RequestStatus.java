package com.vacation.platform.stayfinder.corpuser.entity;

import com.vacation.platform.stayfinder.common.ErrorType;
import com.vacation.platform.stayfinder.common.StayFinderException;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.Map;

@Slf4j
@Getter
@RequiredArgsConstructor
@AllArgsConstructor
public enum RequestStatus {
    PENDING(1, "신청중"),
    APPROVED(2, "승인"),
    REJECTED(3, "거절");

    private int code;
    private String desc;

    public static RequestStatus getRequestStatus(int code) {
        return Arrays.stream(RequestStatus.values())
                .filter(rs -> rs.getCode() == code)
                .findFirst()
                .orElseThrow(() -> new StayFinderException(ErrorType.SYSTEM_ERROR,
                        Map.of("code is not valid", code),
                        log::error));
    }

}
