package com.kgav.gw.assetmanage.asset.controller

// 애노테이션 연결
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.bind.annotation.*

// 관련 파일 연결
import com.kgav.gw.assetmanage.asset.model.UserModel
import com.kgav.gw.assetmanage.asset.service.UserService

//@CrossOrigin(origins = ["http://localhost:5173"])
@RestController
@RequestMapping("/api/v1/user")
class UserController(private val userService: UserService) {

    @PostMapping("/login")
    fun login(@RequestBody userModel: UserModel): String {
        println("login 컨트롤러 도달")
        println(userModel)
        return if (userService.getLogin(userModel)) "로그인 되었습니다."
        else "로그인 과정중 문제가 발생하였습니다."
    }


}