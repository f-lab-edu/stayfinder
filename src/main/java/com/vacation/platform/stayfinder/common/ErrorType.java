package com.vacation.platform.stayfinder.common;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorType {
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "1001", "유저가 존재하지 않습니다.", "요청값이 존재하지 않습니다."),
    DUPLICATE_NICK_NAME(HttpStatus.CONFLICT, "1002", "동일한 닉네임이 사용중입니다.", "다른 값을 입력해주세요."),
    DUPLICATE_TERMS_TITLE(HttpStatus.CONFLICT, "1003", "동일한 타이틀 이름이 존재합니다.", "다른 값을 입력해주세요."),
    TERMS_NOT_FOUND(HttpStatus.NOT_FOUND, "1004", "termsDto 데이터가 미존재 합니다.", "요청값이 존재하지 않습니다."),


    SYSTEM_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "9001", "미확인 에러 발생했습니다.", "관리자에게 문의 해주세요."),
    DB_ERROR(HttpStatus.INTERNAL_SERVER_ERROR,"9002", "DB 에러가 발생했습니다.", "관리자에게 문의 해주세요.")

    ;


    private final HttpStatus httpStatus;
    private final String code;
    private final String internalMessage;
    private final String externalMessage;
}
