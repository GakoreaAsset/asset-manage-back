package com.kgav.gw.assetmanage.ipmanage.mapper

// 애노테이션 연결
import com.kgav.gw.assetmanage.ipmanage.dto.request.IpdetailRequest
import com.kgav.gw.assetmanage.ipmanage.dto.request.IplistRequest
import com.kgav.gw.assetmanage.ipmanage.dto.request.IpmodifyRequset
import com.kgav.gw.assetmanage.ipmanage.dto.response.IpdetailResponse
import com.kgav.gw.assetmanage.ipmanage.dto.response.IplistResponse
import org.apache.ibatis.annotations.Mapper

// 관련 파일 연결

@Mapper
interface IpMapper {
    fun countip(iplistrequest: IplistRequest): Int
    fun listip(iplistrequest: IplistRequest): List<IplistResponse>
    fun detailip(ipdetailRequest: IpdetailRequest): IpdetailResponse
    fun modifyip(ipmodifyRequset: IpmodifyRequset): Int

}
