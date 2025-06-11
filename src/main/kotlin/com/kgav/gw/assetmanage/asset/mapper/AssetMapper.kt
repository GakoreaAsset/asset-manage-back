package com.kgav.gw.assetmanage.asset.mapper

// 애노테이션 연결
import org.apache.ibatis.annotations.Mapper

// 관련 파일 연결
import com.kgav.gw.assetmanage.asset.dto.response.AslistResponse
import com.kgav.gw.assetmanage.asset.dto.request.AsaddRequest
import com.kgav.gw.assetmanage.asset.dto.request.AsdetailRequest
import com.kgav.gw.assetmanage.asset.dto.request.AsdetailhistoryRequest
import com.kgav.gw.assetmanage.asset.dto.request.AslistRequest
import com.kgav.gw.assetmanage.asset.dto.request.AsmodifyRequest
import com.kgav.gw.assetmanage.asset.dto.response.AsdetailResponse
import com.kgav.gw.assetmanage.asset.dto.response.AsdetailhistoryResponse
import com.kgav.gw.assetmanage.asset.model.AssetModel

@Mapper
interface AssetMapper {
    fun countAsset(aslistRequest: AslistRequest): Int
    fun listAsset(aslistRequest: AslistRequest): List<AslistResponse>
    fun addAsset(asaddRequest: AsaddRequest): Int
    fun modifyAsset(asmodifyRequest: AsmodifyRequest): Int
    fun addhistoryAsset(asmodifyRequest: AsmodifyRequest): Int
    fun detailAsset(asdetailRequest: AsdetailRequest): AsdetailResponse
    fun detailhistoryAsset(asdetailhistoryRequest: AsdetailhistoryRequest): List<AsdetailhistoryResponse>
}
