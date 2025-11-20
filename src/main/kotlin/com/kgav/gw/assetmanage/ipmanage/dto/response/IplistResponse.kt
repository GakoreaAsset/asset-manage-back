package com.kgav.gw.assetmanage.ipmanage.dto.response

data class IplistResponse(
    val seq : Int,
    val ipaddr : String?,
    val deptnm : String?,
    val usernm : String?,
    val pubipaddr : String?,
    val etc : String?,
    val ipyn : String?
)
