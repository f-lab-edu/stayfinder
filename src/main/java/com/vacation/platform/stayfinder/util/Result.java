package com.vacation.platform.stayfinder.util;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Result<T> {

    private ResponseCode code;
    private String message;
    private T data;

    public Result(ResponseCode code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public static <T> Result<T> success(T data) {
        return new Result<>(ResponseCode.SUCCESS, ResponseCode.SUCCESS.getCustomMessage(), data);
    }

    public static <T> Result<T> success() {
        return new Result<>(ResponseCode.SUCCESS, ResponseCode.SUCCESS.getCustomMessage(), null);
    }

    public static <T> Result<T> fail(ResponseCode code, String message, T data) {
        return new Result<>(code, message, data);
    }

    public static <T> Result<T> fail(ResponseCode code, String message) {
        return new Result<>(code, message, null);
    }



}
