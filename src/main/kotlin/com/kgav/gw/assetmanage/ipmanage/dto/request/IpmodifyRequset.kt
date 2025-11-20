package com.kgav.gw.assetmanage.ipmanage.dto.request

data class IpmodifyRequset(
    val seq : Int,
    val deptnm : String?,
    val usernm : String?,
    val pubipaddr : String?,
    val etc : String?,
    val ipyn : String?,
    var regid : String?,
    var regip : String
)
