package com.vacation.platform.stayfinder.terms.entity;


import com.vacation.platform.stayfinder.common.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@ToString
@Entity
@NoArgsConstructor
@Data
@Table
@EqualsAndHashCode(callSuper = true)
public class TermsSub extends BaseEntity {

    @Id
    private int termsMainId;

    @Id
    private int version;

    private String termsDetailsTitle;

    private String termsDetailsContent;

    private boolean isActive;
}
