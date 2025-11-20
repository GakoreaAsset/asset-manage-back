package com.kgav.gw.config

import org.apache.ibatis.session.SqlSessionFactory
import org.mybatis.spring.SqlSessionFactoryBean
import org.mybatis.spring.SqlSessionTemplate
import org.mybatis.spring.annotation.MapperScan
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.jdbc.DataSourceBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.io.Resource
import org.springframework.core.io.support.PathMatchingResourcePatternResolver
import org.springframework.jdbc.datasource.DataSourceTransactionManager
import javax.sql.DataSource

@Configuration
@MapperScan(basePackages = ["com.kgav.gw.mealmanage.empuse.mapper"], sqlSessionFactoryRef = "mealSqlSessionFactory")
class MealDataSourceConfig {

    @Bean(name = ["mealDataSource"])
    @ConfigurationProperties(prefix = "spring.datasource.meal.hikari")
    fun mealDataSource(): DataSource {
        return DataSourceBuilder.create().build()
    }

    @Bean(name = ["mealSqlSessionFactory"])
    fun mealSqlSessionFactory(@Qualifier("mealDataSource") dataSource: DataSource): SqlSessionFactory {
        val factoryBean = SqlSessionFactoryBean()
        factoryBean.setDataSource(dataSource)

        // type-aliases-package 설정
        factoryBean.setTypeAliasesPackage("com.kgav.gw.mealmanage.empuse.dto.request")

        // mapper-locations 설정
        val resolver = PathMatchingResourcePatternResolver()
        val resources: Array<Resource> = resolver.getResources("classpath*:mapper/meal/*.xml")
        factoryBean.setMapperLocations(*resources)

        return factoryBean.`object`!!
    }

    @Bean(name = ["mealSqlSessionTemplate"])
    fun mealSqlSessionTemplate(@Qualifier("mealSqlSessionFactory") sqlSessionFactory: SqlSessionFactory): SqlSessionTemplate {
        return SqlSessionTemplate(sqlSessionFactory)
    }

    @Bean(name = ["mealTransactionManager"])
    fun mealTransactionManager(@Qualifier("mealDataSource") dataSource: DataSource): DataSourceTransactionManager {
        return DataSourceTransactionManager(dataSource)
    }
}