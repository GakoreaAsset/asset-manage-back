package com.kgav.gw.mealmanage.empuse.controller

import com.kgav.gw.assetmanage.asset.dto.request.AslistRequest
import com.kgav.gw.mealmanage.empuse.dto.request.EmpuseRequest
import com.kgav.gw.mealmanage.empuse.dto.response.EmpuseResponse
import com.kgav.gw.mealmanage.empuse.service.EmpuseService
import org.springframework.web.bind.annotation.*
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


// 월별 사원 사용 내역
@RestController
@RequestMapping("/api/v1/meal")
class EmpuseController (private val empuseService: EmpuseService) {


    // 월별 사원 사용 검색
    @PostMapping("/checkempuse")
    fun checkempuse(@RequestBody empuseRequest: EmpuseRequest): EmpuseResponse {
        println("checkempuse 컨트롤러 도달")
        println("empuseRequest 정보: $empuseRequest")
        return empuseService.checkempuse(empuseRequest)
    }


}