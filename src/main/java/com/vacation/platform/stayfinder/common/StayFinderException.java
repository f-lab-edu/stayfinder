package com.vacation.platform.stayfinder.common;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Consumer;


@Getter
@Slf4j
public class StayFinderException extends RuntimeException{
    private final ErrorType errorType;
    private final Map<String, Object> parameter;               // 추가 파라미터
    private final Consumer<String> logLevel;      // 로그 레벨을 처리하는 Consumer
    private final Exception cause;                // 원인 예외

    public  StayFinderException(ErrorType errorType, Map<String, Object> parameter, Consumer<String> logLevel) {
        this.errorType = errorType;
        this.parameter = parameter;
        this.logLevel = logLevel;
        this.cause = null;
    }

    public StayFinderException(ErrorType errorType, Map<String, Object> parameter, Consumer<String> logLevel, Exception cause) {
        super(errorType.getExternalMessage(), cause);
        this.errorType = errorType;
        this.parameter = parameter;
        this.logLevel = logLevel;
        this.cause = cause;

        if(cause != null) {
            logLevel.accept(errorType.getInternalMessage() + "\n" + Arrays.toString(cause.getStackTrace()));
        } else {
            logLevel.accept(errorType.getInternalMessage() + "\n" + getMessage());
        }
    }

}
