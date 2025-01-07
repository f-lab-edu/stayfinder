package com.vacation.platform.stayfinder.terms.entity;


import com.vacation.platform.stayfinder.common.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@ToString
@Entity
@NoArgsConstructor
@Data
@Table
@EqualsAndHashCode(callSuper = true)
public class TermsSub extends BaseEntity {

    @Id
    @ManyToOne
    @JoinColumn(name = "terms_id")
    private Terms termsMainId;

    @Column(name = "version", nullable = false)
    private int version;

    @Column(name = "terms_details_title", nullable = false, length = 100)
    private String termsDetailsTitle;

    @Column(name = "terms_details_content", nullable = false, length = 10000)
    private String termsDetailsContent;

    @Column(name = "is_active", nullable = false, length = 1)
    private boolean isActive;
}
