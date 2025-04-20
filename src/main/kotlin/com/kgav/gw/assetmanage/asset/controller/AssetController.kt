package com.kgav.gw.assetmanage.asset.controller

// 애노테이션 연결
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.bind.annotation.*

// 관련 파일 연결
import com.kgav.gw.assetmanage.asset.model.AssetModel
import com.kgav.gw.assetmanage.asset.service.AssetService


@RestController
@RequestMapping("/api/v1/assets")
class AssetController(private val assetService: AssetService) {

    @GetMapping
    fun getAll(): List<AssetModel> = assetService.getAssets()

    /*
    @PostMapping
    fun add(@RequestBody assetModel: AssetModel): String {
        return if (assetService.addAsset(assetModel)) "정상적으로 등록 되었습니다."
        else "등록과정중 문제가 발생하였습니다."
    }
    */
}