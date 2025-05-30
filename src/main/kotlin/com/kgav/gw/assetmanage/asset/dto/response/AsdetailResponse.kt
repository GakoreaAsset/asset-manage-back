package com.kgav.gw.assetmanage.asset.dto.response

// 코틀린 data class는 31개 까지만 가능하다 
data class AsdetailResponse(
    val itemdcd: String?,
    val item4nm: String?,
    val ano: Int?,
    val acdid: String?,
    val anm: String?,
    val amodelno: String?,
    val amodelnm: String?,
    val conm: String?,
    val acorpcd: String?,
    val acorp: String?,
    val apart: String?,
    val auser: String?,
    val myear: String?,
    val mcorp: String?,
    val statenm: String?,
    val astate: String?,
    val regid: String?,
    val regip: String?,
    val regdt: String?,
    val attr1: String?,
    val attr2: String?,
    val attr3: String?,
    val attr4: String?,
    val attr5: String?,
    val iyear: String?,
    val aplace: String?,
    val spec: String?,
    val price: String?,
    val update : Boolean?
)
