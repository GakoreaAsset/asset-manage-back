// 원래 스프링 부트 버전이 3.4.4 엿지만 해당 버전은 마이바티스가 호환이 되지 않기 때문에 호환이 가능한 가장 최신버전인 3.2.5로 낮추고
// 관련하여 마이바티스도 3.0.2가 아닌 3.0.3버전으로 에러를 해결했다.
plugins {
    kotlin("jvm") version "1.9.25"
    kotlin("plugin.spring") version "1.9.25"
    id("org.springframework.boot") version "3.2.5"
    // id("org.springframework.boot") version "3.4.4"
    id("io.spring.dependency-management") version "1.1.7"
}

group = "com.kgav.gw"
version = "0.0.1-SNAPSHOT"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}

repositories {
    mavenCentral()
}

dependencies {

    // mybatis와 JPA 구성
    runtimeOnly("com.mysql:mysql-connector-j")
    implementation("org.springframework.boot:spring-boot-starter-jdbc")
    // implementation("org.springframework.boot:spring-boot-starter-data-jpa")     // jpa
    implementation("org.mybatis.spring.boot:mybatis-spring-boot-starter:3.0.3") // mybatis
    // implementation("org.mybatis.spring.boot:mybatis-spring-boot-starter:3.0.2")

    // 코틀린 기본 의존성
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin") //JSON 변환 모듈
    implementation(kotlin("stdlib-jdk8")) // 코틀린 표준 라이브러리

    // 스프링 코틀린 필수 의존
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.springframework.boot:spring-boot-starter-web")
    developmentOnly("org.springframework.boot:spring-boot-devtools")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
    implementation("org.springframework.boot:spring-boot-starter-security")

}

kotlin {
    compilerOptions {
        freeCompilerArgs.addAll("-Xjsr305=strict")
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}

tasks.withType<ProcessResources> {
    from("src/main/resources")
}