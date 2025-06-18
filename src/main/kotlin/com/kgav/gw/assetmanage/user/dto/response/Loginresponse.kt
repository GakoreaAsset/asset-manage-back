package com.kgav.gw.assetmanage.user.dto.response

data class Loginresponse(
    val accessToken: String,      // 발급된 AccessToken
    val refreshToken: String,     // 발급된 RefreshToken
)
