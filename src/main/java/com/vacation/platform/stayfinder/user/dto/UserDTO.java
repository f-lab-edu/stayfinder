package com.vacation.platform.stayfinder.user.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class UserDTO {

    @Data
    @NoArgsConstructor
    public static class saveDTO {
        private String email;
        private String password;
        private String nickName;
        private String phoneNumber;
        private String birthday;
        private String gender;
    }

}
