package com.kgav.gw.assetmanage.jwt

import com.kgav.gw.assetmanage.config.ParameterProperties
import jakarta.servlet.http.HttpServletResponse
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configurers.CorsConfigurer
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.authentication.AuthenticationFailureHandler
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

@Configuration
@EnableWebSecurity
class SecurityConfig( private val userDetailsService: UserDetailsService, private val parameterProperties: ParameterProperties) { // 반드시 주입

    // 비밀번호 암호화
    @Bean
    fun passwordEncoder(): PasswordEncoder {
        println("passwordEncoder 도달")
        return BCryptPasswordEncoder()
    }

    // AuthenticationManager 빈 등록 (스프링부트 3.x 이상은 직접 등록)
    @Bean
    fun authenticationManager( authenticationConfiguration: AuthenticationConfiguration ): AuthenticationManager {
        println("authenticationManager 도달")
        return authenticationConfiguration.authenticationManager
    }

    // SecurityFilterChain 설정 스프링부트 3.X 이상에서는 이렇게 해야한다.
    @Bean
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
        println("securityFilterChain 도달")

        http.csrf { it.disable() } // JWT에서는 CSRF 불필요
            .cors { corsCustomizer(it) }
            .authorizeHttpRequests { authz -> authz.requestMatchers("/api/v1/user/**").permitAll() // user관련 요청은 전부 허용
                                                   .anyRequest().authenticated()
            }
            .sessionManagement { session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS) }
            .userDetailsService(userDetailsService)
            .addFilterBefore(JwtAuthorizationFilter(parameterProperties), UsernamePasswordAuthenticationFilter::class.java)  // 필터 등록

        return http.build()
    }

    // 스프링 시큐리티 6.x 이상에서는 Cors설정을 Bean에 상속되게 해야한다.
    fun corsCustomizer(cors: CorsConfigurer<HttpSecurity>) {
        cors.configurationSource {
            val configuration = org.springframework.web.cors.CorsConfiguration()
            configuration.allowedOrigins = listOf("http://localhost:5173")  // 프론트 주소
            configuration.allowedMethods = listOf("GET", "POST", "PUT", "DELETE", "OPTIONS")
            configuration.allowedHeaders = listOf("*")
            configuration.exposedHeaders = listOf("*")
            configuration.allowCredentials = true  // 쿠키 포함 허용
            configuration
        }
    }


    @Bean
    fun authenticationFailureHandler(): AuthenticationFailureHandler {
        println("authenticationFailureHandler 도달")
        return AuthenticationFailureHandler { request, response, exception ->
            response.status = HttpServletResponse.SC_UNAUTHORIZED  // 401
            response.contentType = "application/json"
            response.writer.write("{\"error\": \"아이디 또는 비밀번호가 올바르지 않습니다.\"}")
        }
    }

}