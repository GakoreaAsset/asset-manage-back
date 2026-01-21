package com.kgav.gw.user.controller

import com.kgav.gw.jwt.JwtUtil
import com.kgav.gw.user.dto.request.Loginrequest
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.BadCredentialsException
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
    fun login(@RequestBody loginrequest: Loginrequest, httpresponse : HttpServletResponse): ResponseEntity<String> {
        println("login 컨트롤러 도달")
//        println(userModel)

        return try {
        // 인증을 위해 UsernamePasswordAuthenticationToken 생성
        val authToken = UsernamePasswordAuthenticationToken(loginrequest.userid, loginrequest.userpw)
        // 스프링 시큐리티에 인증을 위임
        val authentication = authenticationManager.authenticate(authToken)

        // 인증 성공시 토큰 발급 부분
        // 인증 성공시 principal을 꺼냄
        val principal = authentication.principal as UserDetails

        // JWT 토큰 발급
        // 원래는 principal.username은 ID를 말하는거지만 변형하여 이름을 넣도록 만들어 두었음
        val accessToken = jwtUtil.generateAccessToken(principal.username, principal.authorities.map { it.authority }, loginrequest.userid)
        val refreshToken = jwtUtil.generateRefreshToken(principal.username, principal.authorities.map { it.authority }, loginrequest.userid)

        // SameSite 강제 세팅방법 (해당 maxage를 통해 토큰이 쿠키에 들어있는 시간 조절)
        // HTTP 환경에선 Secure; 속성 제거 그리고 SameSite=None 사용불가 Lax 또는 Strict 사용
        httpresponse.setHeader("Set-Cookie","accessToken=$accessToken; Max-Age=900; Path=/; HttpOnly; SameSite=Lax")
        httpresponse.addHeader("Set-Cookie","refreshToken=$refreshToken; Max-Age=86400; Path=/; HttpOnly; SameSite=Lax")
//        httpresponse.setHeader("Set-Cookie","accessToken=$accessToken; Max-Age=1800; Path=/; Secure; HttpOnly; SameSite=None") // 크로스사이트에서는 SameSite=None 필수
//        httpresponse.addHeader("Set-Cookie","refreshToken=$refreshToken; Max-Age=86400; Path=/; Secure; HttpOnly; SameSite=None")

        println("AccessToken : $accessToken, RefreshToken : $refreshToken")

        ResponseEntity.ok("로그인성공")
        } catch (e: BadCredentialsException) {
            // 비밀번호 오류 시 명확한 응답 반환
            ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("아이디 또는 비밀번호가 틀렸습니다.")
        } catch (e: Exception) {
            // 기타 예외 처리
            ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("서버 오류가 발생했습니다.")
        }
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

            // roles 숫자 문자 리스트 가능하기 때문에 try cathch를 통한 받기
            val roles = try {
                reToken.getClaim("roles").asList(String::class.java)
            } catch (e: Exception) {
                reToken.getClaim("roles").asString()?.let { listOf(it) } ?: emptyList()
            }

            // 유저명
            val username = reToken.subject
            val userId = reToken.getClaim("userId").asString()

            // 새로운 토큰 생성
            val newAccessToken = jwtUtil.generateAccessToken(username, roles, userId)

            response.setHeader("Set-Cookie", "accessToken=$newAccessToken; Max-Age=1800; Path=/; HttpOnly; SameSite=Lax")
//            response.setHeader("Set-Cookie", "accessToken=$newAccessToken; Max-Age=1800; Path=/; Secure; HttpOnly; SameSite=None")

            return ResponseEntity.ok("갱신완료")
        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("갱신실패")
    }

    // 아이디찾기

    // 임시비밀번호

    // 비밀번호 변경

    // 개인정보 변경


}