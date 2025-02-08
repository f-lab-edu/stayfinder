package com.vacation.platform.stayfinder.common;

import com.vacation.platform.stayfinder.util.StayFinderResponseDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@Slf4j
@RestControllerAdvice
public class StayFinderExceptionHandler {


    @Order(1)
    @ExceptionHandler(StayFinderException.class)
    public ResponseEntity<StayFinderResponseDTO<?>> handleStayFinderException(StayFinderException e) {
        ErrorType error = e.getErrorType();

        return ResponseEntity.status(error.getHttpStatus())
                .body(new StayFinderResponseDTO<>(error.getCode(), error.getExternalMessage(), e.getParameter()));
    }

    @Order(2)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<StayFinderResponseDTO<?>> handleValidationException(MethodArgumentNotValidException e) {
        List<String> errorMessages = e.getBindingResult().getFieldErrors()
                .stream()
                .map(FieldError::getDefaultMessage).toList();

        log.info("MethodArgumentNotValidException {}", errorMessages.get(0));

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new StayFinderResponseDTO<>(
                        ErrorType.VALID_ERROR.getCode(),
                        errorMessages.get(0), // 첫 번째 에러 메시지만 응답
                        e
                ));
    }

    @Order(3)
    @ExceptionHandler(Exception.class)
    public ResponseEntity<StayFinderResponseDTO<?>> handleGenericException(Exception e) {

        log.error("Exception StackTrace {}", (Object) e.getStackTrace());
        log.error("Exception Message {}", e.getMessage());

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new StayFinderResponseDTO<>(
                        ErrorType.SYSTEM_ERROR.getCode(),
                        ErrorType.SYSTEM_ERROR.getExternalMessage(),
                        null
                ));
    }
}
