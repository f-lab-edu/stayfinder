package com.vacation.platform.api.certify.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CertifyResponseDto {
    private String responseCode;
    private String responseMessage;
    private Integer certifyNumber;
    private String phoneNumber;
}
