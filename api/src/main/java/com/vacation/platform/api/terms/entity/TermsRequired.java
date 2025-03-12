package com.vacation.platform.api.terms.entity;


import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@Getter
@AllArgsConstructor
public enum TermsRequired {
    NON(0, false),
    Required(1, true);

    private final int number;
    private final boolean isRequired;


    public static boolean getIsRequired(int number) {
        return Arrays.stream(TermsRequired.values())
                .filter(termsRequired -> termsRequired.number == number)
                .map(TermsRequired::isRequired)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("number data is not matched"));
    }


}
