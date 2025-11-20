package com.kgav.gw.mealmanage.empuse.mapper

import com.kgav.gw.mealmanage.empuse.dto.request.EmpuseRequest
import com.kgav.gw.mealmanage.empuse.dto.response.EmpuseResponse
import org.apache.ibatis.annotations.Mapper

@Mapper
interface EmpuseMapper {
    fun checkempuse(empuseRequest: EmpuseRequest) : EmpuseResponse
}