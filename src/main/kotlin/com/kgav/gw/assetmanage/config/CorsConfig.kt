package com.kgav.gw.assetmanage.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.CorsRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
class CorsConfig : WebMvcConfigurer {

    override fun addCorsMappings(registry: CorsRegistry) {
        registry.addMapping("/**")                                            // 모든 URL 패턴에 대해 CORS 설정을 적용
//            .allowedOrigins("http://localhost:5173", "http://localhost:5173")   // 주소에 대한 허용
            .allowedOrigins("*")
            .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")         // 허용할 HTTP 메서드
            .allowedHeaders("*")                                                // 헤더 허용
//            .allowCredentials(true)                                        // 쿠키 전송 허용
    }
}