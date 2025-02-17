package com.vacation.platform.stayfinder.corpuser.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class CorpUserRequestDTO {
    @NotBlank(message = "사업자명은 필수 입니다.")
    private String businessName;

    @NotBlank(message = "사업자 번호는 필수 입니다.")
    private String businessLicense;

    @NotBlank(message = "사업자 주소는 필수 입니다.")
    private String businessAddress;

    @NotBlank(message = "대표자명은 필수 입니다.")
    private String rprsName;
}
