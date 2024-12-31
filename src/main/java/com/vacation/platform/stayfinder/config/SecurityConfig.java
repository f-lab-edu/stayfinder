package com.vacation.platform.stayfinder.config;


import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable);
        http
                .headers((headerConfig) ->
                        headerConfig.frameOptions(HeadersConfigurer.FrameOptionsConfig::disable));

        http
                .authorizeHttpRequests((authorizationManagerRequestMatcherRegistry) ->
                        authorizationManagerRequestMatcherRegistry
                                .requestMatchers(PathRequest.toH2Console()).permitAll()
//                                .requestMatchers("**/**").permitAll()
//                                .anyRequest().authenticated() // 위에 패턴 이외에는 인증해야한다.
                                .anyRequest().permitAll()
                );
        return http.build();
    }

}
