package com.vacation.platform.stayfinder.certify.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CertifyReqId implements Serializable {
    private Long id;
    private Long userId;
}
