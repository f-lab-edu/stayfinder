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
    DTO_NOT_FOUND(HttpStatus.NOT_FOUND, "1004", "요청 데이터가 미존재 합니다.", "요청값이 존재하지 않습니다."),
    TERMS_NOT_FOUND(HttpStatus.NOT_FOUND, "1005", "조회된 약관이 없습니다.", "요청하신 데이터를 확인해주세요."),
    CERTIFY_NOT_VALID(HttpStatus.BAD_REQUEST, "1006", "핸드폰 인증이 처리되지 않았습니다.", "요청 값을 확인해주세요."),
    CERTIFY_TRY_NUMBER(HttpStatus.BAD_REQUEST, "1007", "핸드폰 인증 요청 횟수가 5회를 초과했습니다.", "요청 데이터를 확인해주세요."),

    Nurigo_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "1008", "핸드폰 인증 API 오류 입니다.", "시스템 에러 입니다."),

    TERMS_NOT_AGREEMENT(HttpStatus.BAD_REQUEST, "1009", "약관 동의 요청이 잘못되었습니다.", "요청한 값을 확인해주세요"),

    SYSTEM_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "9001", "미확인 에러 발생했습니다.", "관리자에게 문의 해주세요."),
    DB_ERROR(HttpStatus.INTERNAL_SERVER_ERROR,"9002", "DB 에러가 발생했습니다.", "관리자에게 문의 해주세요.")

    ;


    private final HttpStatus httpStatus;
    private final String code;
    private final String internalMessage;
    private final String externalMessage;
}
