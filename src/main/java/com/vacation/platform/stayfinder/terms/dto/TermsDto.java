package com.vacation.platform.stayfinder.terms.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class TermsDto {
    private String mainTitle;
    private String subTitle;
    private String detailContent;
    private int isRequired;
    private boolean isCompulsion;   // 기존꺼 삭제 후 강제 등록
    private int termsMainId;
    private int version;
}