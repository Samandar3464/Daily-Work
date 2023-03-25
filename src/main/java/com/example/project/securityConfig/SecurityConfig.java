package com.example.project.securityConfig;

import com.example.project.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableMethodSecurity
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final AuthService authService;
    private final String[] USER_CAN_ENTER_GET =
            new String[]{
                    "/api/v1/person/**",
                    "/api/v1/work/**"
            };
    private final String[] USER_CAN_ENTER_POST =
            new String[]{
                    "/api/v1/person/**",
                    "/api/v1/work/**"
            };
    private final String[] USER_CAN_ENTER_PUT =
            new String[]{
                    "/api/v1/person/**",
                    "/api/v1/work/updateCategory/*"
            };
    private final String[] USER_CAN_ENTER_DELETE =
            new String[]{
                    "/api/v1/work/deleteById/*"
            };
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeHttpRequests()
                .requestMatchers(HttpMethod.GET, USER_CAN_ENTER_GET).permitAll()
                .requestMatchers(HttpMethod.POST, USER_CAN_ENTER_POST).permitAll()
                .requestMatchers(HttpMethod.PUT, USER_CAN_ENTER_PUT).permitAll()
                .requestMatchers(HttpMethod.DELETE, USER_CAN_ENTER_DELETE).permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .logout()
                .logoutUrl("/logout")
                .logoutSuccessUrl("/")
                .deleteCookies("JSESSIONID");

        return http.build();
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(authService);
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        return daoAuthenticationProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder authenticationManager =
                http.getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManager.authenticationProvider(authenticationProvider());
        return authenticationManager.build();
    }
}


