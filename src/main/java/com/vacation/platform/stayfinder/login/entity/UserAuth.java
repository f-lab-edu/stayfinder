package com.vacation.platform.stayfinder.login.entity;

import com.vacation.platform.stayfinder.common.BaseEntity;
import com.vacation.platform.stayfinder.user.entity.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "user_auth", uniqueConstraints = {@UniqueConstraint(
        name = "id_userId",
        columnNames = {"id", "user_id"}
)})
@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString
@IdClass(UserAuthId.class)
@EqualsAndHashCode(callSuper = true)
public class UserAuth extends BaseEntity {
    @Id
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(nullable = false)
    private String refreshToken;

    @Column(nullable = false)
    private LocalDateTime expiresAt;

    @ManyToOne
    @JoinColumn(name = "user_id", insertable = false, updatable = false)
    private User user;
}
