package com.vacation.platform.stayfinder.common;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.function.Consumer;


@Getter
@RequiredArgsConstructor
public class StayFinderException extends RuntimeException{
    private final ErrorType errorType;
    private final Object parameter;               // 추가 파라미터
    private final Consumer<String> logLevel;      // 로그 레벨을 처리하는 Consumer
    private final Exception cause;                // 원인 예외
}
