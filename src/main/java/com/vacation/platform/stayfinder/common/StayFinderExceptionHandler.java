package com.vacation.platform.stayfinder.common;

import com.vacation.platform.stayfinder.util.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class StayFinderExceptionHandler {

    @ExceptionHandler(StayFinderException.class)
    public ResponseEntity<Result<?>> handleStayFinderException(StayFinderException e) {
        ErrorType error = e.getErrorType();
        log.error("StayFinderException: {}", error.getInternalMessage());

        return ResponseEntity.status(error.getHttpStatus())
                .body(new Result<>(error.getCode(), error.getExternalMessage(), null));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Result<?>> handleGenericException(Exception e) {
        log.error("Exception: {}", e.getMessage());

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new Result<>(ErrorType.SYSTEM_ERROR.getCode(), ErrorType.SYSTEM_ERROR.getExternalMessage(), null));
    }

}
