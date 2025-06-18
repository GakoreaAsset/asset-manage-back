package com.kgav.gw.assetmanage.jwt

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.kgav.gw.assetmanage.config.ParameterProperties
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.beans.factory.annotation.Value
import org.springframework.web.filter.OncePerRequestFilter
import org.springframework.security.core.authority.SimpleGrantedAuthority // 권한을 스프링 시큐리티가 이해하게 Wrapping
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken // 인증토큰
import org.springframework.security.core.context.SecurityContextHolder

class JwtAuthorizationFilter( parameterProperties: ParameterProperties ) : OncePerRequestFilter() {

    // 시크릿키를 불러와서 알고리즘에 등록한다
    private val algorithm = Algorithm.HMAC256(parameterProperties.secret)

    override fun doFilterInternal(request: HttpServletRequest, response: HttpServletResponse, filterChain: FilterChain) {
        println("doFilterInternal 도달")

        // 리프레시 토큰 요청은 필터에서 무시하고 넘김
        if (request.requestURI.contains("/api/v1/user/refresh")) {
            println("refresh 요청이므로 accessToken 검증 생략")
            filterChain.doFilter(request, response)
            return
        }

        // 쿠키에서 토큰을 읽는다 (Authorization 헤더가 아닌 쿠키에서 읽음)
        val token = request.cookies?.find { it.name == "accessToken" }?.value

        println("accessToken 쿠키 값: $token")

        if (token == null) {
            println("accessTokeni 없음, 필터 통과")
            filterChain.doFilter(request, response)
            return
        }

        try {
            val decodedJWT = JWT.require(algorithm).build().verify(token)

                val username = decodedJWT.subject
//                val roles = decodedJWT.getClaim("roles").asList(String::class.java)
                val roles = decodedJWT.getClaim("roles")?.asList(String::class.java) ?: listOf()

                val authorities = roles.map { SimpleGrantedAuthority(it) }
                val auth = UsernamePasswordAuthenticationToken(username, null, authorities)
                SecurityContextHolder.getContext().authentication = auth

        } catch (e: Exception) {
            e.printStackTrace() // 또는 log.error 사용
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "유효하지 않은 토큰")
            return
        }

        filterChain.doFilter(request, response)
    }
}