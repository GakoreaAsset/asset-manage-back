package com.kgav.gw.assetmanage.jwt

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

class JwtAuthenticationFilter(
    private val authManager: AuthenticationManager
) : UsernamePasswordAuthenticationFilter() {

    init {
        setFilterProcessesUrl("/api/v1/user/login")  // 로그인 URL
    }

    override fun attemptAuthentication(request: HttpServletRequest, response: HttpServletResponse): Authentication {
        // 요청 바디에서 JSON으로 username, password 파싱
        val creds = jacksonObjectMapper().readValue(request.inputStream, Map::class.java)
        val authToken = UsernamePasswordAuthenticationToken(
            creds["username"], creds["password"]
        )
        return authManager.authenticate(authToken)
    }

    override fun successfulAuthentication(
        request: HttpServletRequest, response: HttpServletResponse,
        chain: FilterChain, authResult: Authentication
    ) {
        val principal = authResult.principal as org.springframework.security.core.userdetails.User
        // 액세스·리프레시 토큰 생성
        val accessToken = JwtUtil.generateAccessToken(principal.username, principal.authorities.map { it.authority })
        val refreshToken = JwtUtil.generateRefreshToken(principal.username)

        // 응답 헤더 또는 바디에 토큰 전달 (예: JSON)
        response.contentType = "application/json"
        response.writer.write(
            jacksonObjectMapper().writeValueAsString(
                mapOf("accessToken" to accessToken, "refreshToken" to refreshToken)
            )
        )
    }
}
