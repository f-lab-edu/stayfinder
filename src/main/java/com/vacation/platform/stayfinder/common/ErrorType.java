package com.vacation.platform.stayfinder.common;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorType {
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "1001", "유저가 존재하지 않습니다.", "요청값이 존재하지 않습니다."),
    DUPLICATE_NICK_NAME(HttpStatus.CONFLICT, "1002", "동일한 닉네임이 사용중입니다.", "다른 값을 입력해주세요."),

    SYSTEM_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "9999", "9999 에러 발생했습니다.", "에러가 발생했습니다.")
    ;

    private final HttpStatus httpStatus;
    private final String code;
    private final String internalMessage;
    private final String externalMessage;
}
