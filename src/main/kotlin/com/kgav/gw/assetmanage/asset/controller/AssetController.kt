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
    fun addAsset(asaddRequest: AsaddRequest): Int {
        println("addAsset 컨트롤러 도달")
        println(asaddRequest)
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
    fun modifyAsset(asmodifyRequest: AsmodifyRequest): Int {
        println("modifyAsset 컨트롤러 도달")
        println(asmodifyRequest)
        return assetService.modifyAsset(asmodifyRequest)
    }
}