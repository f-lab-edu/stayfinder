package com.vacation.platform.stayfinder.util;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ResponseCode {
    SUCCESS(HttpStatus.OK, "성공"),
    BAD_REQUEST(HttpStatus.BAD_REQUEST, "잘못된 요청 입니다."),
    UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "권한 없음"),
    FORBIDDEN(HttpStatus.FORBIDDEN, "금지된 요청"),
    NOT_FOUND(HttpStatus.NOT_FOUND, "리소스를 찾을 수 없음"),
    NOT_CERTIFY_DATA(HttpStatus.BAD_REQUEST, "요청 값을 확인해주세요"),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "서버상 에러가 존재합니다."),;

    private final HttpStatus httpStatus;
    private final String customMessage;

    ResponseCode(HttpStatus httpStatus, String customMessage) {
        this.httpStatus = httpStatus;
        this.customMessage = customMessage;
    }
}
