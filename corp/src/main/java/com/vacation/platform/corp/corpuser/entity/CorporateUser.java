package com.vacation.platform.corp.corpuser.entity;

import com.vacation.platform.api.common.BaseEntity;
import com.vacation.platform.api.user.entity.Role;
import com.vacation.platform.corp.reservation.entity.Reservation;
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
	@JoinColumn(name = "corp_user_id", referencedColumnName = "corpUserId")
	private CorpUser corpUser;

	@OneToMany(mappedBy = "corporateUser", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Room> rooms = new ArrayList<>();

	@OneToMany(mappedBy = "confirmedBy", cascade = CascadeType.ALL)
	private List<Reservation> confirmedReservations = new ArrayList<>();

}
