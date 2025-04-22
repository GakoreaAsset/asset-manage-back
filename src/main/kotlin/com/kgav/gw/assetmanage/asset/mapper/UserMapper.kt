package com.kgav.gw.assetmanage.asset.mapper

// 애노테이션 연결
import org.apache.ibatis.annotations.Mapper

// 관련 파일 연결
import com.kgav.gw.assetmanage.asset.model.UserModel
import org.apache.ibatis.annotations.Param

@Mapper
interface UserMapper {

    fun getUser(userModel: UserModel): Int
}