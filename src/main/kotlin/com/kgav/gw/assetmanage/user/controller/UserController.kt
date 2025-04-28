package com.kgav.gw.assetmanage.user.controller

import com.kgav.gw.assetmanage.user.model.UserModel
import com.kgav.gw.assetmanage.user.service.UserService
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

//@CrossOrigin(origins = ["http://localhost:5173"])
@RestController
@RequestMapping("/api/v1/user")
class UserController(private val userService: UserService) {

    @PostMapping("/login")
    fun login(@RequestBody userModel: UserModel): Int {
        println("login 컨트롤러 도달")
//        println(userModel)
        val code: Int = userService.getLogin(userModel)
//        println(code)
        // 추후 기능이 많아져 코드별로 기능을 나누면 숫자를 받아서 해당하는 코드숫자를 반환하게 만들예정
        // 0 로그인실패 1 로그인 성공
        return code
    }


}