package com.kgav.gw.mealmanage.empuse.service

import com.kgav.gw.mealmanage.empuse.dto.request.EmpuseRequest
import com.kgav.gw.mealmanage.empuse.dto.response.EmpuseResponse
import com.kgav.gw.mealmanage.empuse.mapper.EmpuseMapper
import org.springframework.stereotype.Service

@Service
class EmpuseService(private  val empuseMapper: EmpuseMapper) {

    fun checkempuse(empuseRequest: EmpuseRequest): EmpuseResponse {
        return empuseMapper.checkempuse(empuseRequest)
    }
}