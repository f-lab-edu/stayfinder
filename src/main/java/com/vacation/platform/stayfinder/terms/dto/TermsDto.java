package com.vacation.platform.stayfinder.terms.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Data
@RequiredArgsConstructor
public class TermsDto {
    private Long termsMainId;           /* 약관 메인 ID */
    private String mainTitle;           /* 약관 메인 제목 */
    private String subTitle;            /* 약관 상세 제목 */
    private String detailContent;       /* 약관 상세 내용 */
    private Boolean isRequired;         /* 약관 필수 여부 */
    private Long version;               /* 약관 메인 ID */
    private Boolean isCompulsion;       /* 기존 약관이 존재한다면 강제 업데이트 */
    private Boolean isAgreement;         /* 약관 동의 여부 */
    private Integer sortSeq;            /* 약관 순서 */

    @Data
    @RequiredArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class MainResponseDto {
        private Long termsId;
        private String termsMainTitle;
        private Boolean isTermsRequired;
    }

    @Data
    @RequiredArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class SubResponseDto {
        private String termsDetailsTitle;
        private String termsDetailsContent;
        private Boolean isActive;
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
        private LocalDateTime createAt;
    }
}