package com.kgav.gw.assetmanage.asset.dto.request

data class AsmodifyRequest(
    // 기존내용 업데이트할때 필요한것
    val update : Boolean?,
    val ano : Int?,

    // 신규내용 업데이트시 필요한것
    val itemdcd : String?,
    val anm : String?,
    val acorpcd : String?,
    val apart : String?,
    val auser : String?,
    val myear : String?,
    val mcorp : String?,
    val astate : String?,
    val regid : String?,
    var regip : String?,
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
