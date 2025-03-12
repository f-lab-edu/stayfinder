package com.vacation.platform.api.common;

import com.vacation.platform.api.util.StayFinderResponseDTO;
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

        getStackTrace(e);

        List<String> errorMessages = e.getBindingResult().getFieldErrors()
                .stream()
                .map(FieldError::getDefaultMessage).toList();

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

        getStackTrace(e);

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new StayFinderResponseDTO<>(
                        ErrorType.SYSTEM_ERROR.getCode(),
                        ErrorType.SYSTEM_ERROR.getExternalMessage(),
                        null
                ));
    }

    private void getStackTrace(Exception e) {
        log.error("Validation Exception Message: {}", e.getMessage());

        for (StackTraceElement stackTraceElement : e.getStackTrace()) {
            log.error(stackTraceElement.toString());
        }
    }
}
