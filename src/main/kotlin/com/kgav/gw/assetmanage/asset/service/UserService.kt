package com.kgav.gw.assetmanage.asset.service

// 애노테이션 연결
import org.springframework.stereotype.Service

// 관련 파일 연결
import com.kgav.gw.assetmanage.asset.mapper.UserMapper
import com.kgav.gw.assetmanage.asset.model.UserModel

@Service
class UserService (private val userMapper: UserMapper) {

    fun getLogin(userModel: UserModel): Boolean {
        return userMapper.getUser(userModel) > 0
    }

}
