package com.vacation.platform.corp.corperation.util;

import com.vacation.platform.api.common.ErrorType;
import com.vacation.platform.api.common.StayFinderException;
import com.vacation.platform.api.util.JwtUtil;
import com.vacation.platform.corp.corperation.entity.CorporateUser;
import com.vacation.platform.corp.corperation.repository.CorporateUserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class UtilService {
	private final CorporateUserRepository corporateUserRepository;

	private final JwtUtil jwtUtil;

	public CorporateUser getCorporateUser(String token) {
		String email = jwtUtil.getUserEmail(token);
		return corporateUserRepository.findByEmail(email).orElseThrow(
				() -> new StayFinderException(ErrorType.BUSINESS_IS_NOT_EXIST, Map.of(), log::error)
		);
	}

}
