package com.kgav.gw.assetmanage.filter

import jakarta.servlet.*
import jakarta.servlet.http.HttpServletRequest
import org.springframework.stereotype.Component
import java.io.IOException

// 필터는 jwt 사용할때도 필요한 중요요소
// Spring Boot가 자동으로 등록
@Component
class LoggingFilter : Filter {

    @Throws(IOException::class, ServletException::class)
    override fun doFilter(
        request: ServletRequest,
        response: ServletResponse,
        chain: FilterChain
    ) {
        val httpRequest = request as HttpServletRequest

        // 요청 URI, 메서드, Origin 정보 로그 출력
        println("요청 URI: ${httpRequest.requestURI}")
        println("요청 Method: ${httpRequest.method}")
        println("요청 Origin: ${httpRequest.getHeader("Origin")}")

        // 다음 필터 또는 컨트롤러로 요청을 넘김
        chain.doFilter(request, response)
    }
}