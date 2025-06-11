package com.kgav.gw.assetmanage.jwt

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import java.util.*

object JwtUtil {
    private const val SECRET = "YOUR_SECRET_KEY"           // 실제 배포 시 환경변수로 관리하세요
    private val algorithm = Algorithm.HMAC256(SECRET)

    // 액세스 토큰 생성 (30분)
    fun generateAccessToken(username: String, roles: List<String>): String {
        val now = Date()
        val expireDate = Date(now.time + 30 * 60 * 1000)   // 30분 후 만료
        return JWT.create()
            .withSubject(username)                         // 페이로드: 사용자명
            .withClaim("roles", roles)                 // 페이로드: 권한 목록
            .withIssuedAt(now)                            // 발급 시각
            .withExpiresAt(expireDate)                    // 만료 시각
            .sign(algorithm)
    }

    // 리프레시 토큰 생성 (예: 7일)
    fun generateRefreshToken(username: String): String {
        val now = Date()
        val expireDate = Date(now.time + 7L * 24 * 60 * 60 * 1000) // 7일 후
        return JWT.create()
            .withSubject(username)
            .withIssuedAt(now)
            .withExpiresAt(expireDate)
            .sign(algorithm)
    }

    // 토큰 검증 및 디코딩
    fun verifyToken(token: String) =
        JWT.require(algorithm).build().verify(token)
}
