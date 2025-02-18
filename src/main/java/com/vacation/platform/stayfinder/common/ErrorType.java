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
    TERMS_DIDNT_AGREEMENT(HttpStatus.BAD_REQUEST, "1010", "필수 약관 동의를 하지 않았습니다.", "요청한 값이 잘못 되었습니다."),
    CERTIFY_PHONE_NUM_NOT_MATCHED(HttpStatus.BAD_REQUEST, "1011", "핸드폰 인증 번호가 불일치 합니다.", "요청한 값이 잘못 되었습니다."),
    CERTIFY_IS_COMPLETE(HttpStatus.MULTI_STATUS, "1012", "핸드폰 인증 처리가 이미 완료 되었습니다.", "요청값을 확인해 주세요."),
    DUPLICATE_EMAIL(HttpStatus.CONFLICT, "1013", "가입된 이메일이 존재합니다.", "요청한 데이터를 확인해주세요."),
    CERTIFY_IS_NOT_COMPLETE(HttpStatus.NOT_FOUND, "1014", "핸드폰 인증이 미완료 되었습니다.", "요청값을 확인해주세요"),
    USER_PASSWORD_NOT_MATCHED(HttpStatus.BAD_REQUEST, "1015", "비밀번호가 불일치 합니다.", "요청값을 확인해 주세요."),
    USER_EMAIL_NOT_VALID(HttpStatus.BAD_REQUEST, "1016", "이메일의 형식이 잘못 되었습니다.", "요청값을 확인해 주세요."),
    USER_PHONE_NUM_NOT_MATCHED(HttpStatus.BAD_REQUEST, "1017", "인증받은 핸드폰 번호가 아닙니다.", "요청값을 확인해 주세요."),
    USER_EMAIL_NOT_EXIST(HttpStatus.BAD_REQUEST, "1018", "이메일이 없습니다.", "요청값을 확인해주세요."),
    VALID_ERROR(HttpStatus.BAD_REQUEST, "1019", "잘못된 값입니다.", "요청값을 확인해주세요."),
    USER_AUTH_DUPLICATION(HttpStatus.BAD_REQUEST, "1020", "Refresh Token은 한개만 사용 가능합니다.", "요청값을 확인해주세요."),
    REFRESH_TOKEN_NOT_VALID(HttpStatus.BAD_REQUEST, "1021", "Refresh Token이 존재하지 않습니다.", "요청값을 확인해 주세요."),
    ACCESS_TOKEN_NOT_VALID(HttpStatus.BAD_REQUEST, "1022", "Access Token이 유효하지 않습니다.", "요청값을 확인해 주세요."),
    TOKEN_IS_NOT_VALID(HttpStatus.BAD_REQUEST, "1023", "Token과 email의 값이 맞지 않습니다.", "요청값을 확인해 주세요."),
    TOKEN_IS_EMPTY(HttpStatus.BAD_REQUEST, "1024", "Token이 존재하지 않습니다.", "요청값을 확인해 주세요."),
    USER_PHONE_NUMBER_DUPLICATION(HttpStatus.BAD_REQUEST, "1025", "가입된 핸드폰번호가 존재합니다.", "요청값을 확인해 주세요."),
    CORP_USER_REQUEST_DUPLICATION(HttpStatus.BAD_REQUEST, "1026", "이미 기신청된 사업자 번호가 존재합니다.", "요청값을 확인해주세요."),
    INVALID_DATE_RANGE(HttpStatus.BAD_REQUEST, "1027", "조회 종료 날짜는 조회 시작 날짜보다 과거 일수 없습니다.", "요청값을 확인해주세요."),
    BUSINESS_LICENSE_NOT_VALID(HttpStatus.BAD_REQUEST, "1028", "사업자 등록증은 필수 입니다.", "요청값을 확인해주세요."),
    SYSTEM_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "9001", "미확인 에러 발생했습니다.", "관리자에게 문의 해주세요."),
    DB_ERROR(HttpStatus.INTERNAL_SERVER_ERROR,"9002", "DB 에러가 발생했습니다.", "관리자에게 문의 해주세요."),
    FILE_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "9003", "파일 저장 에러가 발생했습니다.", "관리자에게 문의해주세요.")

    ;


    private final HttpStatus httpStatus;
    private final String code;
    private final String internalMessage;
    private final String externalMessage;
}
