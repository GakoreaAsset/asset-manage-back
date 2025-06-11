package com.kgav.gw.assetmanage.jwt

import org.springframework.context.annotation.Bean
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.web.SecurityFilterChain

@Bean
fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
    return http
        .csrf().disable()                              // JWT 사용 시 CSRF 비활성
        .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        .and()
        .authorizeHttpRequests { auth ->
            auth.requestMatchers("/auth/**").permitAll()  // 로그인·리프레시 엔드포인트 공개
                .anyRequest().authenticated()             // 나머지 요청은 인증 필요
        }
        .addFilter(JwtAuthenticationFilter(authenticationManager(http)))
        .addFilterAfter(JwtAuthorizationFilter(), JwtAuthenticationFilter::class.java)
        .build()
}

// AuthenticationManager 빈 등록
@Bean
fun authenticationManager(http: HttpSecurity): AuthenticationManager {
    val authManagerBuilder = http.getSharedObject(AuthenticationManagerBuilder::class.java)
    // UserDetailsService 및 PasswordEncoder 설정
    // authManagerBuilder.userDetailsService(...).passwordEncoder(...)
    return authManagerBuilder.build()
}
