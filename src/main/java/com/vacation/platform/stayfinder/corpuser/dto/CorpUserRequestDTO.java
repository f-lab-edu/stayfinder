package com.vacation.platform.stayfinder.corpuser.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class CorpUserRequestDTO {
    private String businessName;
    private String businessLicense;
    private String businessAddress;
}
