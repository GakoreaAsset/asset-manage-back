package com.kgav.gw.assetmanage.ipmanage.service

// 애노테이션 연결
import com.kgav.gw.assetmanage.ipmanage.dto.request.IpdetailRequest
import com.kgav.gw.assetmanage.ipmanage.dto.request.IplistRequest
import com.kgav.gw.assetmanage.ipmanage.dto.request.IpmodifyRequset
import com.kgav.gw.assetmanage.ipmanage.dto.response.IpdetailResponse
import com.kgav.gw.assetmanage.ipmanage.dto.response.IplistResponse
import com.kgav.gw.assetmanage.ipmanage.mapper.IpMapper
import org.springframework.stereotype.Service

// 관련 파일 연결

@Service
class IpService(private val ipMapper: IpMapper) {

    fun getIppage(iplistrequest: IplistRequest): Int {
        return ipMapper.countip(iplistrequest)
    }

    fun ipPage(iplistrequest: IplistRequest): List<IplistResponse> {
        return ipMapper.listip(iplistrequest)
    }

    fun detailIp(ipdetailRequest: IpdetailRequest): IpdetailResponse {
        return ipMapper.detailip(ipdetailRequest)
    }

    fun modifyIp(ipmodifyRequset: IpmodifyRequset): Int {
        return ipMapper.modifyip(ipmodifyRequset)
    }
}