package com.kgav.gw.assetmanage.config

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Configuration

@Configuration
class ParameterProperties {
    @Value("\${jwt.secret}")
    lateinit var secret: String // 시크릿 키 주입
}