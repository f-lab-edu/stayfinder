package com.vacation.platform.corp.corperation.entity;

import com.vacation.platform.api.common.BaseEntity;
import com.vacation.platform.api.user.entity.Role;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@Table(name = "corporate_user")
@NoArgsConstructor
@AllArgsConstructor
public class CorporateUser extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(nullable = false, unique = true)
	private Long id;

	@Column(name = "email", nullable = false, unique = true, columnDefinition = "이메일")
	private String email;

	@Column(name = "password", nullable = false, columnDefinition = "비밀번호")
	private String password;

	@Column(name = "phone_number", nullable = false, unique = true, columnDefinition = "전화번호")
	private String phoneNumber;

	@Column(columnDefinition = "권한")
	@Enumerated(EnumType.STRING)
	private Role role;

	@OneToOne
	@JoinColumn(name = "corp_user_id")
	private Corporation corporation;
}
