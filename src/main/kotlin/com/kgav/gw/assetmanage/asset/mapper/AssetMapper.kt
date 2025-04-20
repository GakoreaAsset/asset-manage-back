package com.kgav.gw.assetmanage.asset.mapper

// 애노테이션 연결
import org.apache.ibatis.annotations.Mapper
import org.apache.ibatis.annotations.Param

// 관련 파일 연결
import com.kgav.gw.assetmanage.asset.model.AssetModel

@Mapper
interface AssetMapper {
    fun searchAsset(): List<AssetModel>
    // fun insertAsset(@Param("AssetModel") assetModel: AssetModel): Int
}
