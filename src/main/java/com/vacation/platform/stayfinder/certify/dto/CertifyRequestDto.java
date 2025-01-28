package com.vacation.platform.stayfinder.certify.dto;

import com.vacation.platform.stayfinder.terms.dto.TermsDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CertifyRequestDto {
    private String phoneNumber;             /* 핸드폰 번호 */
    private List<TermsDto> termsDtoList;    /* 약관 동의 리스트 */
    private Integer reqCertifyNumber;        /* 약관 인증 번호 */
    private Integer tryNumber;               /* 인증 요청 횟수 */
    private Boolean isCertify;              /* 인증 완료 여부 */
}
