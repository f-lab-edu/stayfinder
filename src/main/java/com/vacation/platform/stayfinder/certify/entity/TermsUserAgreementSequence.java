package com.vacation.platform.stayfinder.certify.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Table(name = "terms_user_agreement_sequence")
@NoArgsConstructor
public class TermsUserAgreementSequence {

    @Id
    @Column(name = "sequence_name", nullable = false, length = 50)
    private String sequenceName;

    @Column(name = "next_val", nullable = false)
    private Long nextVal;

}
