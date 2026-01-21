package com.kgav.gw.jwt

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.kgav.gw.config.ParameterProperties
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.filter.OncePerRequestFilter
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException

class JwtAuthorizationFilter( parameterProperties: ParameterProperties ) : OncePerRequestFilter() {

    // 시크릿키를 불러와서 알고리즘에 등록한다
    private val algorithm = Algorithm.HMAC256(parameterProperties.secret)

    override fun doFilterInternal(request: HttpServletRequest, response: HttpServletResponse, filterChain: FilterChain) {
        println("doFilterInternal 도달")

        var uri = request.requestURI

        // 리프레시 토큰 요청 및 로그인 요청은 필터에서 무시하고 넘김
        // request.requestURI.contains("/api/v1/user/refresh") || request.requestURI.contains("/api/v1/user/login") contains 보안 문제가 있어서 사용 X
        // contains = 문자열 중간 포함여부 || startsWith = 문자열 시작 여부
        if (uri.startsWith("/api/v1/user/refresh") || uri.startsWith("/api/v1/user/login") || uri.startsWith("/error")) {
            println("refresh 요청 또는 login 요청 이므로 accessToken 검증 생략")
            filterChain.doFilter(request, response)
            return
        }

        // 쿠키에서 토큰을 읽는다 (Authorization 헤더가 아닌 쿠키에서 읽음)
        val token = request.cookies?.find { it.name == "accessToken" }?.value

//        실사용 서버에서는 리프레쉬 토큰관련 내용을 인증필터에 넣지 않는다
//        val refreshToken = request.cookies?.find { it.name == "refreshToken" }?.value
//
//        println("accessToken 쿠키 값: $token")
//        println("refreshToken 쿠키 값: $refreshToken")
//
//        // 엑세스 토큰은 없고 리프레쉬 토큰만 있을때
//        if (token == null && refreshToken != null) {
//            println("accessToken은 없고 refreshToken은 있음 → 프론트가 /refresh 요청 유도하도록 401 반환")
////            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Access Token 없음. Refresh 요청 필요")
//            response.status = HttpServletResponse.SC_UNAUTHORIZED
//
//            return
//        } else if (token == null) {
//            println("token 없음 생략")
//            filterChain.doFilter(request, response)
//            return
//        }

        // 쿠키에 엑세스토큰이 있을때 인증을 시도
        try {
            if (token != null) {
                println("인증 시도중...")
                val decodedJWT = JWT.require(algorithm).build().verify(token)
                val username = decodedJWT.subject
                val userId = decodedJWT.getClaim("userId").asString()
                val roles = decodedJWT.getClaim("roles")?.asList(String::class.java) ?: listOf()

                val authorities = roles.map { SimpleGrantedAuthority(it) }
                val auth = UsernamePasswordAuthenticationToken(userId, null, authorities)
                SecurityContextHolder.getContext().authentication = auth
            }

            filterChain.doFilter(request, response)
        }   catch (e: Exception) {
                e.printStackTrace() // 또는 log.error 사용
//                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "유효하지 않은 토큰")
//                response.status = HttpServletResponse.SC_UNAUTHORIZED
                SecurityContextHolder.clearContext()
                throw AuthenticationCredentialsNotFoundException("token값 없음")
//                return
        }

        if (token == null) {
            SecurityContextHolder.clearContext()
            filterChain.doFilter(request, response)
            return
        }

        filterChain.doFilter(request, response)
    }
}