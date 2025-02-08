package com.vacation.platform.stayfinder.util;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StayFinderResponseDTO<T> {

    private String code;
    private String message;
    private T data;

    public StayFinderResponseDTO(String code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public static <T> StayFinderResponseDTO<T> success(T data) {
        return new StayFinderResponseDTO<>("0000", ResponseCode.SUCCESS.getCustomMessage(), data);
    }

    public static <T> StayFinderResponseDTO<T> success() {
        return new StayFinderResponseDTO<>("0000", ResponseCode.SUCCESS.getCustomMessage(), null);
    }


}
