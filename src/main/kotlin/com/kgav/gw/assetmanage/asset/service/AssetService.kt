package com.kgav.gw.assetmanage.asset.service

// 애노테이션 연결
import com.kgav.gw.assetmanage.asset.dto.AslistRequest
import org.springframework.stereotype.Service

// 관련 파일 연결
import com.kgav.gw.assetmanage.asset.mapper.AssetMapper
import com.kgav.gw.assetmanage.asset.dto.AslistRespond

@Service
class AssetService(private val assetMapper: AssetMapper) {

    fun countAsset(): Int {
        return assetMapper.countAsset()
    }

    fun listAsset(aslistRequest: AslistRequest): List<AslistRespond> {
        return  assetMapper.listAsset(aslistRequest)
    }

}