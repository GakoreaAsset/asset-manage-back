package com.kgav.gw.jwt

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.auth0.jwt.interfaces.DecodedJWT
import com.kgav.gw.config.ParameterProperties
import org.springframework.stereotype.Component
import java.util.*

@Component
class
JwtUtil(private val parameterProperties: ParameterProperties) {

    private val secret = parameterProperties.secret
    private val algorithm = Algorithm.HMAC256(secret)

    // 액세스 토큰 생성
    fun generateAccessToken(username: String, roles: List<String>, userId: String): String {
        println("generateAccessToken 도착")
        val now = Date()
        val expireDate = Date(now.time + 30 * 60 * 1000)   // 30분 후 만료
        return JWT.create()
            .withSubject(username)                         // 페이로드: 사용자명
            .withClaim("roles", roles)                 // 페이로드: 권한 목록
            .withClaim("userId", userId)             // 페이로드: 유저ID
            .withIssuedAt(now)                            // 발급 시각
            .withExpiresAt(expireDate)                    // 만료 시각
            .sign(algorithm)
    }

    // 리프레시 토큰 생성
    fun generateRefreshToken(username: String, roles: List<String>, userId: String): String {
        println("generateRefreshToken 도착")
        val now = Date()
        val expireDate = Date(now.time + 24 * 60 * 60 * 1000)
        return JWT.create()
            .withSubject(username)
            .withClaim("roles", roles)
            .withClaim("userId", userId)
            .withIssuedAt(now)
            .withExpiresAt(expireDate)
            .sign(algorithm)
    }

    // 토큰 검증
    fun validate(token: String): Boolean {
        return try {
            JWT.require(Algorithm.HMAC256(secret)).build().verify(token)
            true
        } catch (e: Exception) {
            false
        }
    }

    // 토큰 디코딩
    fun decodingToken(token: String): DecodedJWT {
        return JWT.require(algorithm)
            .build()
            .verify(token)
    }
}
