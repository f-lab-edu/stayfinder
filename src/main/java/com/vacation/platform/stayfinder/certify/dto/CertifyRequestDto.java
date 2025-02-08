package com.vacation.platform.stayfinder.certify.dto;

import com.vacation.platform.stayfinder.terms.dto.TermsDto;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CertifyRequestDto {
    @NotBlank(message = "핸드폰 번호는 필수 입니다.")
    private String phoneNumber;             /* 핸드폰 번호 */
    @NotBlank(message = "시스템 에러 입니다.")
    private List<TermsDto> termsDtoList;    /* 약관 동의 리스트 */
    @NotBlank(message = "핸드폰 인증번호는 필수 입니다.")
    private Integer reqCertifyNumber;        /* 핸드폰 인증 번호 */
    @NotBlank(message = "시스템 에러 입니다.")
    private Integer tryNumber;               /* 인증 요청 횟수 */
    @NotBlank(message = "시스템 에러 입니다.")
    private Boolean isCertify;              /* 인증 완료 여부 */
}
