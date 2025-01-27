package com.vacation.platform.stayfinder.common;

import com.vacation.platform.stayfinder.util.StayFinderResponseDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class StayFinderExceptionHandler {


    @Order(1)
    @ExceptionHandler(StayFinderException.class)
    public ResponseEntity<StayFinderResponseDTO<?>> handleStayFinderException(StayFinderException e) {
        ErrorType error = e.getErrorType();

        return ResponseEntity.status(error.getHttpStatus())
                .body(new StayFinderResponseDTO<>(error.getCode(), error.getExternalMessage(), null));
    }

    @Order(2)
    @ExceptionHandler(Exception.class)
    public ResponseEntity<StayFinderResponseDTO<?>> handleGenericException(Exception e) {

        log.error("sssss {}", (Object) e.getStackTrace());

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new StayFinderResponseDTO<>(
                        ErrorType.SYSTEM_ERROR.getCode(),
                        ErrorType.SYSTEM_ERROR.getExternalMessage(),
                        null
                ));
    }
}
