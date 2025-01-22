package com.vacation.platform.stayfinder.terms.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TermsDto {
    private String mainTitle;
    private String subTitle;
    private String detailContent;
    private Boolean isRequired;
    private Boolean isCompulsion;  // 기존 약관이 존재한다면 강제 업데이트
    private Long termsMainId;
    private Long version;
}