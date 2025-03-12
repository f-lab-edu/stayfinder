package com.vacation.platform.corp.corpuser.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class CorpUserDTO {
    private String email;
    private String password;
    private String passwordConfirm;
    private String corpPhoneNumber;
    private String businessLicense;
}
