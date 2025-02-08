package com.vacation.platform.stayfinder.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class UserDTO {

    @Data
    @NoArgsConstructor
    public static class saveDTO {
        @NotBlank(message = "이메일은 필수 입니다.")
        @Email(message = "이메일 형식이 아닙니다.")
        private String email;
        @NotBlank(message = "비밀번호는 필수 입니다.")
        private String password;
        @NotBlank(message = "비밀번호 확인 값을 입력해주세요.")
        private String passwordCheck;
        @NotBlank(message = "닉네임을 입력해주세요.")
        private String nickName;
        @NotBlank(message = "시스템 오류 입니다.")
        private String phoneNumber;
        @NotBlank(message = "생년월일을 입력해주세요.")
        private String birthday;
        @NotBlank(message = "성별은 필수 입니다.")
        private String gender;
    }

}
