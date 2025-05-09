package com.kgav.gw.assetmanage.asset.service

// 애노테이션 연결
import org.springframework.stereotype.Service

// 관련 파일 연결
import com.kgav.gw.assetmanage.asset.mapper.AssetMapper
import com.kgav.gw.assetmanage.asset.dto.response.AslistResponse
import com.kgav.gw.assetmanage.asset.dto.request.AsmodifyRequest
import com.kgav.gw.assetmanage.asset.dto.request.AsaddRequest
import com.kgav.gw.assetmanage.asset.dto.request.AsdetailRequest
import com.kgav.gw.assetmanage.asset.dto.request.AslistRequest
import com.kgav.gw.assetmanage.asset.dto.response.AsdetailResponse

@Service
class AssetService(private val assetMapper: AssetMapper) {

    fun countAsset(): Int {
        return assetMapper.countAsset()
    }

    fun listAsset(aslistRequest: AslistRequest): List<AslistResponse> {
        return assetMapper.listAsset(aslistRequest)
    }

    fun addAsset(asaddRequest: AsaddRequest): Int {
        return assetMapper.addAsset(asaddRequest)
    }

    fun detailAsset(asdetailRequest: AsdetailRequest): AsdetailResponse {
        return assetMapper.detailAsset(asdetailRequest)
    }

    fun modifyAsset(asmodifyRequest: AsmodifyRequest): Int {
        // update 숫자가 1이면 기존내용도 업데이트하면서 신규내용도 업데이트하기
        if (asmodifyRequest.update == 1) {
            assetMapper.addhistoryAsset(asmodifyRequest)
            val modify:Int = assetMapper.modifyAsset(asmodifyRequest)
            println("기존내용 업데이트 하는 버전")
            println(modify)
            return modify
        } else {
            println("기존내용 업데이트 안하는 버전")
            return assetMapper.modifyAsset(asmodifyRequest)
        }
    }
}