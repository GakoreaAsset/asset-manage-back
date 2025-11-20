package com.kgav.gw.assetmanage.swlicense.service

// 애노테이션 연결
import com.kgav.gw.assetmanage.swlicense.dto.request.SwaddRequest
import com.kgav.gw.assetmanage.swlicense.dto.request.SwdetailRequest
import com.kgav.gw.assetmanage.swlicense.dto.request.SwlistRequest
import com.kgav.gw.assetmanage.swlicense.dto.request.SwmodifyRequest
import com.kgav.gw.assetmanage.swlicense.dto.response.SwdetailResponse
import com.kgav.gw.assetmanage.swlicense.dto.response.SwlistResponse
import org.springframework.stereotype.Service

// 관련 파일 연결
import com.kgav.gw.assetmanage.swlicense.mapper.SwMapper

@Service
class SwService(private val swMapper: SwMapper) {

    fun getSwpage(swlistRequest: SwlistRequest): Int {
        return swMapper.countsw(swlistRequest)
    }

    fun swPage(swlistRequest: SwlistRequest): List<SwlistResponse> {
        return swMapper.listsw(swlistRequest)
    }

    fun swDetail(swdetailRequest: SwdetailRequest): SwdetailResponse {
        return swMapper.detailsw(swdetailRequest)
    }

    fun swModify(swmodifyRequest: SwmodifyRequest): Int {
        return swMapper.modifysw(swmodifyRequest)
    }

    fun swAdd(swaddRequest: SwaddRequest): Int {
        return swMapper.addsw(swaddRequest)
    }

}