package com.vacation.platform.stayfinder.user.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class TermsSubId implements Serializable {
    private Long termsMainId;
    private int version;
}
