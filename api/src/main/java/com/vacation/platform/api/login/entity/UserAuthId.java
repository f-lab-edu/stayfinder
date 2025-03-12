package com.vacation.platform.api.login.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserAuthId implements Serializable {
    private Long id;
    private Long userId;
}
