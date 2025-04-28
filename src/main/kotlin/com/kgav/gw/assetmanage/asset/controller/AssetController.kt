package com.kgav.gw.assetmanage.asset.controller

// 애노테이션 연결
import com.kgav.gw.assetmanage.asset.dto.AslistRequest
import com.kgav.gw.assetmanage.asset.dto.AslistRespond
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.bind.annotation.*

// 관련 파일 연결
import com.kgav.gw.assetmanage.asset.model.AssetModel
import com.kgav.gw.assetmanage.asset.service.AssetService


@RestController
@RequestMapping("/api/v1/asset")
class AssetController(private val assetService: AssetService) {

     // 자산 페이지 
    @GetMapping("/pagenum")
    fun getPagenum(assetModel: AssetModel): Int {
        println("getPagenum 컨트롤러 도달")
        return assetService.countAsset()
    }

            @PostMapping("/page")
    fun assetPage(@RequestBody aslistRequest: AslistRequest): List<AslistRespond> {
        println("assetPage 컨트롤러 도달")
        return assetService.listAsset(aslistRequest)
    }

    // 자산내역 업데이트시 체크에 대한 값을 받아서 해당 함수에서 로직 처리를 나눈다. if로 가져가기
}