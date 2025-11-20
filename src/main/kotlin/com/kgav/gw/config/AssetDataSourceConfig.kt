package com.kgav.gw.config

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import org.apache.ibatis.session.SqlSessionFactory
import org.mybatis.spring.SqlSessionFactoryBean
import org.mybatis.spring.SqlSessionTemplate
import org.mybatis.spring.annotation.MapperScan
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.jdbc.DataSourceBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import org.springframework.core.io.Resource
import org.springframework.core.io.support.PathMatchingResourcePatternResolver
import org.springframework.jdbc.datasource.DataSourceTransactionManager
import javax.sql.DataSource

@Configuration
@MapperScan(basePackages = ["com.kgav.gw.user.mapper", "com.kgav.gw.assetmanage.asset.mapper", "com.kgav.gw.assetmanage.assethistory.mapper", "com.kgav.gw.assetmanage.ipmanage.mapper", "com.kgav.gw.assetmanage.swlicense.mapper"], sqlSessionFactoryRef = "assetSqlSessionFactory")
class AssetDataSourceConfig {

    @Bean
    @ConfigurationProperties("spring.datasource.asset.hikari")
    fun assetHikariConfig(): HikariConfig = HikariConfig()


    @Bean(name = ["assetDataSource"])
    fun assetDataSource(@Qualifier("assetHikariConfig") config : HikariConfig ): HikariDataSource {
        return HikariDataSource(config)
    }

    @Bean(name = ["assetSqlSessionFactory"])
    fun assetSqlSessionFactory(@Qualifier("assetDataSource") dataSource: HikariDataSource): SqlSessionFactory {
        val factoryBean = SqlSessionFactoryBean()

        // 데이터소스 설정
        factoryBean.setDataSource(dataSource)

        // type-aliases-package 설정
        factoryBean.setTypeAliasesPackage("com.kgav.gw.assetmanage.asset.model,com.kgav.gw.user.model,com.kgav.gw.assetmanage.assethistory.model")

        // mapper-locations 설정
        val resolver = PathMatchingResourcePatternResolver()
        val resources: Array<Resource> = resolver.getResources("classpath*:mapper/asset/*.xml")
        factoryBean.setMapperLocations(*resources)

        return factoryBean.`object`!!
    }

    @Bean(name = ["assetSqlSessionTemplate"])
    fun assetSqlSessionTemplate(@Qualifier("assetSqlSessionFactory") sqlSessionFactory: SqlSessionFactory): SqlSessionTemplate {
        return SqlSessionTemplate(sqlSessionFactory)
    }

    @Bean(name = ["assetTransactionManager"])
    fun assetTransactionManager(@Qualifier("assetDataSource") dataSource: HikariDataSource): DataSourceTransactionManager {
        return DataSourceTransactionManager(dataSource)
    }
}