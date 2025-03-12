package com.vacation.platform.api.certify.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "certify_req_sequence")
@NoArgsConstructor
public class CertifyReqSequence {

    @Id
    @Column(name = "sequence_name", nullable = false, length = 50)
    private String sequenceName;

    @Column(name = "next_val", nullable = false)
    private Long nextVal;

}
