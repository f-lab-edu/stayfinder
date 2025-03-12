package com.vacation.platform.api.terms.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TermsSubId implements Serializable {
    private Long termsId;
    private Long version;
}
