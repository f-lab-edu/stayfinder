package com.vacation.platform.stayfinder.terms.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TermsDto {
    private String mainTitle;
    private String subTitle;
    private String detailContent;
    private boolean isRequired;
    private boolean isCompulsion;  // 기존 약관이 존재한다면 강제 업데이트
    private int termsMainId;
    private int version;
}