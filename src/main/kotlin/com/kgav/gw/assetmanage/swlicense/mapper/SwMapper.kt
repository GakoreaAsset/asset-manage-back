package com.kgav.gw.assetmanage.swlicense.mapper

// 애노테이션 연결
import com.kgav.gw.assetmanage.swlicense.dto.request.SwaddRequest
import com.kgav.gw.assetmanage.swlicense.dto.request.SwdetailRequest
import com.kgav.gw.assetmanage.swlicense.dto.request.SwlistRequest
import com.kgav.gw.assetmanage.swlicense.dto.request.SwmodifyRequest
import com.kgav.gw.assetmanage.swlicense.dto.response.SwdetailResponse
import com.kgav.gw.assetmanage.swlicense.dto.response.SwlistResponse
import org.apache.ibatis.annotations.Mapper

// 관련 파일 연결

@Mapper
interface SwMapper {

    fun countsw(swlistRequest: SwlistRequest): Int
    fun listsw(swlistRequest: SwlistRequest): List<SwlistResponse>
    fun detailsw(swdetailRequest: SwdetailRequest): SwdetailResponse
    fun modifysw(swmodifyRequest: SwmodifyRequest): Int
    fun addsw(swaddRequest: SwaddRequest): Int

}
