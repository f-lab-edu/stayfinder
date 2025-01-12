package com.vacation.platform.stayfinder.certify.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TermsUserAgreementId implements Serializable {
    private Integer id;
    private Integer userId;
    private Integer termsId;
    private Integer version;
}
