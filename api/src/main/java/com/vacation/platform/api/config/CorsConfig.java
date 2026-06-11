//package com.vacation.platform.api.config;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.web.SecurityFilterChain;
//import org.springframework.web.servlet.config.annotation.CorsRegistry;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
//
//@Configuration
//public class CorsConfig {
//
//	@Bean
//	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//		http.cors() // CORS 설정을 Security에 반영
//				.and()
//				.csrf().disable() // 필요 시 CSRF 비활성화
//				.authorizeRequests()
//				.anyRequest().permitAll();
//
//		return http.build();
//	}
//
//	@Bean
//	public CorsFilter corsFilter() {
//		CorsConfiguration config = new CorsConfiguration();
//		config.addAllowedOrigin("http://127.0.0.1:5500"); // 허용할 프론트엔드 주소
//		config.addAllowedMethod("*");
//		config.addAllowedHeader("*");
//		config.setAllowCredentials(true);
//
//		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//		source.registerCorsConfiguration("/**", config);
//
//		return new CorsFilter(source);
//	}
//}
