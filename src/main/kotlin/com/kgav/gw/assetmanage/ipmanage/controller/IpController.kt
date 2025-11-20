package com.kgav.gw.assetmanage.ipmanage.controller

// 애노테이션 연결
import com.kgav.gw.assetmanage.ipmanage.dto.request.IpdetailRequest
import com.kgav.gw.assetmanage.ipmanage.dto.request.IplistRequest
import com.kgav.gw.assetmanage.ipmanage.dto.request.IpmodifyRequset
import com.kgav.gw.assetmanage.ipmanage.dto.response.IpdetailResponse
import com.kgav.gw.assetmanage.ipmanage.dto.response.IplistResponse
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.bind.annotation.*

// 관련 파일 연결
import com.kgav.gw.assetmanage.ipmanage.service.IpService
import jakarta.servlet.http.HttpServletRequest
import java.security.Principal

// 아이피 페이지
@RestController
@RequestMapping("/api/v1/ip")
class IpController(private val ipService: IpService) {

    // 아이피 수량 요청
    @PostMapping("/pagenum")
    fun getIppage(@RequestBody iplistrequest: IplistRequest): Int {
        println("getIppage 컨트롤러 도달")
        return ipService.getIppage(iplistrequest)
    }

    // 아이피 리스트 출력 (19개)
    @PostMapping("/page")
    fun ipPage(@RequestBody iplistrequest: IplistRequest): List<IplistResponse> {
        println("ipPage 컨트롤러 도달")
        println("iplistrequest 정보: $iplistrequest")
        return ipService.ipPage(iplistrequest)
    }

    // 아이피 디테일 출력
    @GetMapping("/detail")
    fun detailIp(ipdetailRequest: IpdetailRequest): IpdetailResponse {
        println("detailIp 컨트롤러 도달")
        println(ipdetailRequest)
        return ipService.detailIp(ipdetailRequest)
    }

    // 아이피 업데이트 입력
    @PostMapping("/modify")
    fun modifyIp(request: HttpServletRequest, @RequestBody ipmodifyRequset: IpmodifyRequset, principal: Principal): Int {
        println("modifyIp 컨트롤러 도달")

        val forwardedIp = request.getHeader("X-Forwarded-For")
        val clientIp = forwardedIp ?: request.remoteAddr
        println("요청 IP: $clientIp")
        println("요청 자산 정보: $ipmodifyRequset")

        // 아이디 변경
        ipmodifyRequset.regid = principal.name.toString()

        if (ipmodifyRequset.regip == "221.163.112.122" ) {
            ipmodifyRequset.regip = clientIp
            println("변경된 내부망IP: $ipmodifyRequset")
        }
        return ipService.modifyIp(ipmodifyRequset)
    }


}