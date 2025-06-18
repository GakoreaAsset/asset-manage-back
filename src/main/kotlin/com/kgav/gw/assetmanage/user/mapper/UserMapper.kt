package com.kgav.gw.assetmanage.user.mapper

import com.kgav.gw.assetmanage.user.model.UserModel
import org.apache.ibatis.annotations.Mapper

@Mapper
interface UserMapper {

    fun findById(userid: String): UserModel?

    fun getUser(userModel: UserModel): Int
}