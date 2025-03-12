package com.vacation.platform.api.login.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Table(name = "user_auth_sequence")
@NoArgsConstructor
public class UserAuthSequence {
    @Id
    @Column(name = "sequence_name", nullable = false, length = 50)
    private String sequenceName;

    @Column(name = "next_val", nullable = false)
    private Long nextVal;
}
