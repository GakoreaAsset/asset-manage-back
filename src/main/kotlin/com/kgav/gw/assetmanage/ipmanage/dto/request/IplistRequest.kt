package com.kgav.gw.assetmanage.ipmanage.dto.request

data class IplistRequest(
    val ipaddr : String?,
    val usernm : String?,
    val deptnm : String?,
    val ipyn : String?,
    var pageNumber: Int
)
