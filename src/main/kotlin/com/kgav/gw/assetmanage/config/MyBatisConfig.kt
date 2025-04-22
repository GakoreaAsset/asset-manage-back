package com.kgav.gw.assetmanage.config

import org.apache.ibatis.session.SqlSessionFactory
import org.mybatis.spring.SqlSessionFactoryBean
import org.mybatis.spring.annotation.MapperScan
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.io.support.PathMatchingResourcePatternResolver
import javax.sql.DataSource

/*
  MyBatis 관련 설정을 수동으로 구성
 */
@Configuration
@MapperScan("com.kgav.gw.assetmanage.asset.mapper") // 매퍼 인터페이스 경로
class MyBatisConfig {

    @Bean
    fun sqlSessionFactory(dataSource: DataSource): SqlSessionFactory {
        val sessionFactoryBean = SqlSessionFactoryBean()

        // 데이터베이스 연결 정보 주입
        sessionFactoryBean.setDataSource(dataSource)

        // XML 매퍼 경로 설정 - resources/mapper/*.xml 구조
        val resolver = PathMatchingResourcePatternResolver()
        sessionFactoryBean.setMapperLocations(*resolver.getResources("classpath:mapper/*.xml"))

        // Kotlin 사용 시 TypeAlias나 기타 설정이 필요할 수도 있음 (생략 가능)
        sessionFactoryBean.setTypeAliasesPackage("com.kgav.gw.assetmanage.asset.model")

        return sessionFactoryBean.`object`!!
    }
}