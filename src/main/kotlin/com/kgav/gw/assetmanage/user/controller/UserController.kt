package com.kgav.gw.assetmanage.user.controller

import com.kgav.gw.assetmanage.user.service.UserService
import com.kgav.gw.assetmanage.jwt.JwtUtil
import com.kgav.gw.assetmanage.user.dto.request.Loginrequest
import com.kgav.gw.assetmanage.user.dto.response.Loginresponse
import com.kgav.gw.assetmanage.user.model.UserModel
import jakarta.servlet.http.Cookie
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.web.bind.annotation.GetMapping


//@CrossOrigin(origins = ["http://localhost:5173"])
@RestController
@RequestMapping("/api/v1/user")
class UserController(private val authenticationManager: AuthenticationManager, private val jwtUtil: JwtUtil) {

    // JWT 토큰 적용한 로그인 방식
    @PostMapping("/login")
    fun login(@RequestBody loginrequest: Loginrequest, httpresponse : HttpServletResponse): ResponseEntity<Loginresponse> {
        println("login 컨트롤러 도달")
//        println(userModel)

        // 인증을 위해 UsernamePasswordAuthenticationToken 생성
        val authToken = UsernamePasswordAuthenticationToken(loginrequest.userid, loginrequest.userpw)
        // 스프링 시큐리티에 인증을 위임
        val authentication = authenticationManager.authenticate(authToken)

        // 인증 성공시 토큰 발급 부분
        // 인증 성공시 principal을 꺼냄
        val principal = authentication.principal as UserDetails

        // JWT 토큰 발급
        // 원래는 principal.username은 ID를 말하는거지만 변형하여 이름을 넣도록 만들어 두었음
        val accessToken = jwtUtil.generateAccessToken(principal.username, principal.authorities.map { it.authority })
        val refreshToken = jwtUtil.generateRefreshToken(principal.username, principal.authorities.map { it.authority })

        val accessCookie = Cookie("accessToken", accessToken).apply {
            isHttpOnly = true       // JS에서 접근 금지
            secure = false           // HTTPS에서만 전송 개발시는 false / 배포시 true
            path = "/"              // 전체 경로에 대해 유효
            maxAge = 60 * 30        // 30분 유효
//            sameSite = "Strict"     // CSRF 방지 강화
        }

        val refreshCookie = Cookie("refreshToken", refreshToken).apply {
            isHttpOnly = true
            secure = false
            path = "/"
            maxAge = 60 * 60 * 24 * 7  // 7일 유효
        }

        // SameSite 강제 세팅방법 (해당 maxage를 통해 토큰이 쿠키에 들어있는 시간 조절)
//        httpresponse.setHeader("Set-Cookie","accessToken=$accessToken; Max-Age=900; Path=/; Secure; HttpOnly; SameSite=Strict")
        httpresponse.setHeader("Set-Cookie","accessToken=$accessToken; Max-Age=1800; Path=/; Secure; HttpOnly; SameSite=None") // 크로스사이트에서는 SameSite=None 필수
        httpresponse.addHeader("Set-Cookie","refreshToken=$refreshToken; Max-Age=604800; Path=/; Secure; HttpOnly; SameSite=None")

        // SameSite 미적용
//        httpresponse.addCookie(accessCookie)
//        httpresponse.addCookie(refreshCookie)

        // LoginResponse 객체 생성 후 반환
        val response = Loginresponse(
            accessToken = accessToken,
            refreshToken = refreshToken
        )
        println("response : $response")

        return ResponseEntity.ok(response)
    }

    // 토큰 인증체크
    @GetMapping("/check")
    fun checkLogin(request: HttpServletRequest): ResponseEntity<String> {
        println("checkLogin")
        val accessToken = request.cookies?.find { it.name == "accessToken" }?.value
        if (accessToken != null && jwtUtil.validate(accessToken)) {
            println("토큰확인 완료")
            return ResponseEntity.ok("로그인됨")
        }
        println("토큰인식 불가")
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("토큰없음")
    }

    // 리프레시
    @PostMapping("/refresh")
    fun refreshToken(request: HttpServletRequest, response: HttpServletResponse): ResponseEntity<String> {
        println("checkLogin")
        val refreshToken = request.cookies?.find { it.name == "refreshToken" }?.value

        if (refreshToken != null && jwtUtil.validate(refreshToken)) {
            val reToken = jwtUtil.decodingToken(refreshToken)

            // roles 클레임 안전하게 파싱
            val roles = try {
                reToken.getClaim("roles").asList(String::class.java)
            } catch (e: Exception) {
                reToken.getClaim("roles").asString()?.let { listOf(it) } ?: emptyList()
            }

            // 유저명
            val username = reToken.subjectㄹ

            // 새로운 토큰 생성
            val newAccessToken = jwtUtil.generateAccessToken(username, roles)

            response.setHeader("Set-Cookie", "accessToken=$newAccessToken; Max-Age=1800; Path=/; Secure; HttpOnly; SameSite=None")

            return ResponseEntity.ok("갱신완료")
        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("갱신실패")
    }
}