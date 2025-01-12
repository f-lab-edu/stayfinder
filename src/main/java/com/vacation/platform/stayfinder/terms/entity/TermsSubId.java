package com.vacation.platform.stayfinder.terms.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TermsSubId implements Serializable {
    private Long termsId;
    private Integer version;
}
