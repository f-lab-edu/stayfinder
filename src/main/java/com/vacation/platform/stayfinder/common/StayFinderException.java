package com.vacation.platform.stayfinder.common;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class StayFinderException extends RuntimeException{
    private final ErrorType errorType;
}
