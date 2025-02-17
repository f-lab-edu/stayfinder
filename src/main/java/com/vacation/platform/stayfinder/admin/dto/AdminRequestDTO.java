package com.vacation.platform.stayfinder.admin.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
public class AdminRequestDTO {


    @Data
    @RequiredArgsConstructor
    public static class CorpUserRequestInquiryDTO {
        @NotBlank(message = "시작 일자는 필수 입니다.")
        private String startDate;
        private String endDate;
        private Integer requestStatus;
    }

    @Data
    @RequiredArgsConstructor
    public static class CorpUserRequestApprovedDTO {
        private Integer corpUserId;
        private String requestStatus;
    }

}
