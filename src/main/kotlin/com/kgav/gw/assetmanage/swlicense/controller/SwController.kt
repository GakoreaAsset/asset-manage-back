package com.kgav.gw.assetmanage.swlicense.controller

// 애노테이션 연결
import com.kgav.gw.assetmanage.asset.dto.request.AsaddRequest
import com.kgav.gw.assetmanage.swlicense.dto.request.SwaddRequest
import com.kgav.gw.assetmanage.swlicense.dto.request.SwdetailRequest
import com.kgav.gw.assetmanage.swlicense.dto.request.SwlistRequest
import com.kgav.gw.assetmanage.swlicense.dto.request.SwmodifyRequest
import com.kgav.gw.assetmanage.swlicense.dto.response.SwdetailResponse
import com.kgav.gw.assetmanage.swlicense.dto.response.SwlistResponse
import com.kgav.gw.assetmanage.swlicense.service.SwService
import jakarta.servlet.http.HttpServletRequest
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.bind.annotation.*
import java.security.Principal

// 관련 파일 연결

// 자산 페이지
@RestController
@RequestMapping("/api/v1/sw")
class SwController(private val swService: SwService) {

    // 라이선스 수량 요청
    @PostMapping("/pagenum")
    fun getSwpage(@RequestBody swlistRequest: SwlistRequest): Int {
        println("getSwpage 컨트롤러 도달")
        return swService.getSwpage(swlistRequest)
    }

    // 라이선스 리스트 출력 (18개)
    @PostMapping("/page")
    fun swPage(@RequestBody swlistRequest: SwlistRequest): List<SwlistResponse> {
        println("swPage 컨트롤러 도달")
        println("swlistRequest 정보: $swlistRequest")
        return swService.swPage(swlistRequest)
    }

    // 라이선스 리스트 출력 (18개)
    @GetMapping("/detail")
    fun swDetail(swdetailRequest: SwdetailRequest): SwdetailResponse {
        println("swDetail 컨트롤러 도달")
        println("swlistRequest 정보: $swdetailRequest")
        return swService.swDetail(swdetailRequest)
    }

    // 라이선스 업데이트 입력
    @PostMapping("/modify")
    fun swModify(request: HttpServletRequest, @RequestBody swmodifyRequest: SwmodifyRequest, principal: Principal): Int {
        println("swModify 컨트롤러 도달")

        val forwardedIp = request.getHeader("X-Forwarded-For")
        val clientIp = forwardedIp ?: request.remoteAddr
        swmodifyRequest.regid = principal.name.toString()

        // 테스트 부분
        println("요청 principal: $principal")
        println("요청 IP: $clientIp")
        println("요청 자산 정보: $swmodifyRequest")

        if (swmodifyRequest.regip == "221.163.112.122" ) {
            swmodifyRequest.regip = clientIp
            println("변경된 내부망IP: $swmodifyRequest")
        }

        return swService.swModify(swmodifyRequest)
    }

    // 라이선스 신규 입력
    @PostMapping("/add")
    fun swAdd(request: HttpServletRequest, @RequestBody swaddRequest: SwaddRequest, principal: Principal): Int {
        println("swAdd 컨트롤러 도달")
        // 프록시 환경일때 X-Forwarded-For 헤더가 존재하면 해당 값을 우선 사용
        val forwardedIp = request.getHeader("X-Forwarded-For")
        // 헤더가 없으면 직접 연결된 클라이언트의 IP 사용
        val clientIp = forwardedIp ?: request.remoteAddr
        swaddRequest.regid = principal.name.toString()

        println("요청 IP: $clientIp")
        println("요청 자산 정보: $swaddRequest")

        // 사내망일경우 내부 적시
        if (swaddRequest.regip == "221.163.112.122" ) {
            swaddRequest.regip = clientIp
            println("변경된 내부망IP: $swaddRequest")
        }
        return swService.swAdd(swaddRequest)
    }

}