package com.vacation.platform.stayfinder.terms.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
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
}