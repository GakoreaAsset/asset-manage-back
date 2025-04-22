package com.kgav.gw.assetmanage.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.config.Customizer
import org.springframework.security.web.util.matcher.AntPathRequestMatcher

@Configuration
class SecurityConfig {

    @Bean
    fun filterChain(http: HttpSecurity): SecurityFilterChain {
        http
            .csrf { it.disable() }                                                  // CSRF 비활성화
            .cors(Customizer.withDefaults())                                        // CORS 설정 허용
            .authorizeHttpRequests {
                it.requestMatchers(AntPathRequestMatcher("/**")).permitAll() // 모든 요청 허용
            }

        return http.build()
    }
}
