package com.kgav.gw.assetmanage.asset.service

// 애노테이션 연결
import org.springframework.stereotype.Service

// 관련 파일 연결
import com.kgav.gw.assetmanage.asset.mapper.AssetMapper
import com.kgav.gw.assetmanage.asset.model.AssetModel

@Service
class AssetService(private val assetMapper: AssetMapper) {

    fun getAssets(): List<AssetModel> {
        return assetMapper.searchAsset()
    }

    /*
    fun addAsset(asset: AssetModel): Boolean {
        return assetMapper.insertAsset(asset) > 0
    }
    */
}