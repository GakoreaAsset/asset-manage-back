package com.kgav.gw.assetmanage.asset.dto.request

data class AsmodifyRequest(
    // 기존내용 업데이트할때 필요한것
    val update : Int?,
    val ano : Int?,
    val itemdcd : String?,

    // 신규내용 업데이트시 필요한것
    val acorpcd : String?,
    val apart : String?,
    val auser : String?,
    val myear : String?,
    val mcorp : String?,
    val astate : String?,
    val regid : String?,
    val regip : String?,
    val attr1 : String?,
    val attr2 : String?,
    val attr3 : String?,
    val attr4 : String?,
    val attr5 : String?,
    val iyear : String?,
    val aplace : String?,
    val spec : String?,
    val acdid : String?
)
