package com.kgav.gw.assetmanage.asset.mapper

// 애노테이션 연결
import com.kgav.gw.assetmanage.asset.dto.AslistRequest
import org.apache.ibatis.annotations.Mapper

// 관련 파일 연결
import com.kgav.gw.assetmanage.asset.dto.AslistRespond

@Mapper
interface AssetMapper {
    fun countAsset(): Int
    fun listAsset(aslistRequest: AslistRequest): List<AslistRespond>
//    fun addAsset(): Int
}
