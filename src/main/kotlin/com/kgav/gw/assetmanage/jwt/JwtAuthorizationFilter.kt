package com.kgav.gw.assetmanage.jwt

import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.web.filter.OncePerRequestFilter

class JwtAuthorizationFilter : OncePerRequestFilter() {
    override fun doFilterInternal(
        request: HttpServletRequest, response: HttpServletResponse, chain: FilterChain
    ) {
        val header = request.getHeader("Authorization")
        if (header != null && header.startsWith("Bearer ")) {
            try {
                val token = header.replace("Bearer ", "")
                val decoded = JwtUtil.verifyToken(token)
                val username = decoded.subject
                val roles = decoded.getClaim("roles").asList(String::class.java)
                // SecurityContext에 인증 정보 설정 (생략)
            } catch (e: Exception) {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "토큰이 유효하지 않습니다.")
                return
            }
        }
        chain.doFilter(request, response)
    }
}
