package com.vacation.platform.stayfinder.corpuser.entity;

import com.vacation.platform.stayfinder.common.BaseEntity;
import com.vacation.platform.stayfinder.reservation.entity.Reservation;
import com.vacation.platform.stayfinder.user.entity.Role;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

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
	private Long corpUserId;

	@Column(name = "email", nullable = false, unique = true, columnDefinition = "이메일")
	private String email;

	@Column(name = "password", nullable = false, columnDefinition = "비밀번호")
	private String password;

	@Column(name = "phone_number", nullable = false, unique = true, columnDefinition = "전화번호")
	private String phoneNumber;

	@Column(columnDefinition = "권한")
	@Enumerated(EnumType.STRING)
	private Role role;

	@Column(nullable = false, columnDefinition = "사업자번호")
	private String businessLicense;

	@OneToOne
	@JoinColumn(name = "corp_user_id")
	private CorpUser corpUser;

	@OneToMany(mappedBy = "corporateUser", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Room> rooms = new ArrayList<>();

	@OneToMany(mappedBy = "confirmedBy", cascade = CascadeType.ALL)
	private List<Reservation> confirmedReservations = new ArrayList<>();
}
