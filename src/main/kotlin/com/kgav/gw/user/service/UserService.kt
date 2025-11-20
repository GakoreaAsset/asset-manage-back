package com.kgav.gw.user.service

import com.kgav.gw.user.mapper.UserMapper
import com.kgav.gw.user.model.UserModel
import org.springframework.stereotype.Service

@Service
class UserService (private val userMapper: UserMapper) {

    fun getLogin(userModel: UserModel): Int {
        return userMapper.getUser(userModel)
    }

}