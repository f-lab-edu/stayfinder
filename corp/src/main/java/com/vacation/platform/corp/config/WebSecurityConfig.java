package com.vacation.platform.corp.config;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig {

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http
				.csrf(AbstractHttpConfigurer::disable);
		http
				.formLogin(AbstractHttpConfigurer::disable);
		http
				.httpBasic(AbstractHttpConfigurer::disable);

		http
				.sessionManagement((session) ->
						session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
		http
				.authorizeHttpRequests((authorizationManagerRequestMatcherRegistry ) -> {
					authorizationManagerRequestMatcherRegistry
							.requestMatchers("/api/v1/corp/request/approval").permitAll()
							.requestMatchers("/api/v1/search/**").permitAll()
							.requestMatchers("/api/v1/corp/**").permitAll()
							.requestMatchers("/api/v1/corp/user/**").hasRole("CORP_USER")
							.requestMatchers(PathRequest.toH2Console()).hasRole("ADMIN")
							.anyRequest().denyAll();
				})
		;

		return http.build();
	}

	@Bean
	public CorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration configuration = new CorsConfiguration();
		configuration.setAllowedOrigins(Arrays.asList("http://localhost:8081", "http://localhost:8082"));  // 8083 포트에서 오는 요청 허용
		configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE"));
		configuration.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type"));
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", configuration);  // 모든 경로에 CORS 적용
		return source;
	}


	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

}
