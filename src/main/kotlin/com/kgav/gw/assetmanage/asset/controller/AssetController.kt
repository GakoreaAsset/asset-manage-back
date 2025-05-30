package com.kgav.gw.assetmanage.asset.controller

// 애노테이션 연결
import com.kgav.gw.assetmanage.asset.dto.request.AsaddRequest
import com.kgav.gw.assetmanage.asset.dto.request.AsdetailRequest
import com.kgav.gw.assetmanage.asset.dto.request.AslistRequest
import com.kgav.gw.assetmanage.asset.dto.response.AslistResponse
import com.kgav.gw.assetmanage.asset.dto.request.AsmodifyRequest
import com.kgav.gw.assetmanage.asset.dto.response.AsdetailResponse
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.bind.annotation.*

// 관련 파일 연결
import com.kgav.gw.assetmanage.asset.model.AssetModel
import com.kgav.gw.assetmanage.asset.service.AssetService
import jakarta.servlet.http.HttpServletRequest

// 자산 페이지
@RestController
@RequestMapping("/api/v1/asset")
class AssetController(private val assetService: AssetService) {

    // 자산 수량 요청  
    @GetMapping("/pagenum")
    fun getPagenum(assetModel: AssetModel): Int {
        println("getPagenum 컨트롤러 도달")
        return assetService.countAsset()
    }
    
    // 자산 리스트 출력 (20개)
    @PostMapping("/page") 
    fun assetPage(@RequestBody aslistRequest: AslistRequest): List<AslistResponse> {
        println("assetPage 컨트롤러 도달")
        return assetService.listAsset(aslistRequest)
    }

    // 자산 신규 입력
    @PostMapping("/add")
    fun addAsset(request: HttpServletRequest, @RequestBody asaddRequest: AsaddRequest): Int {
        println("addAsset 컨트롤러 도달")
        // 프록시 환경일때 X-Forwarded-For 헤더가 존재하면 해당 값을 우선 사용
        val forwardedIp = request.getHeader("X-Forwarded-For")
        // 헤더가 없으면 직접 연결된 클라이언트의 IP 사용
        val clientIp = forwardedIp ?: request.remoteAddr

        println("요청 IP: $clientIp")
        println("요청 자산 정보: $asaddRequest")

        // 사내망일경우 내부 적시
        if (asaddRequest.regip == "221.163.112.122" ) {
            asaddRequest.regip = clientIp
            println("변경된 내부망IP: $asaddRequest")
        }
        return assetService.addAsset(asaddRequest)
    }

    // 자산 디테일 출력
    @GetMapping("/detail")
    fun detailAsset(asdetailRequest: AsdetailRequest): AsdetailResponse {
        println("detailAsset 컨트롤러 도달")
        println(asdetailRequest)
        return assetService.detailAsset(asdetailRequest)
    }

    // 자산 업데이트 입력
    @PostMapping("/modify")
    fun modifyAsset(request: HttpServletRequest, @RequestBody asmodifyRequest: AsmodifyRequest): Int {
        println("modifyAsset 컨트롤러 도달")

        val forwardedIp = request.getHeader("X-Forwarded-For")
        val clientIp = forwardedIp ?: request.remoteAddr
        println("요청 IP: $clientIp")
        println("요청 자산 정보: $asmodifyRequest")

        if (asmodifyRequest.regip == "221.163.112.122" ) {
            asmodifyRequest.regip = clientIp
            println("변경된 내부망IP: $asmodifyRequest")
        }
        return assetService.modifyAsset(asmodifyRequest)
    }
}