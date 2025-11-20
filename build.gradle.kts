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
    runtimeOnly("com.mysql:mysql-connector-j")                          // mysql
    implementation("com.microsoft.sqlserver:mssql-jdbc:12.6.0.jre11")  // mssql https://learn.microsoft.com/ko-kr/sql/connect/jdbc/system-requirements-for-the-jdbc-driver?view=sql-server-ver17
    implementation("org.springframework.boot:spring-boot-starter-jdbc")
    // implementation("org.springframework.boot:spring-boot-starter-data-jpa")     // jpa

    // mybatis
    implementation("org.mybatis:mybatis:3.5.11")
    implementation("org.mybatis:mybatis-spring:3.0.3")
    implementation("org.mybatis.spring.boot:mybatis-spring-boot-starter:3.0.3")
    // implementation("org.mybatis.spring.boot:mybatis-spring-boot-starter:3.0.2")

    // 코틀린 기본 의존성
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin") //JSON 변환 모듈
    implementation(kotlin("stdlib-jdk8")) // 코틀린 표준 라이브러리

    // 스프링 코틀린 필수 의존
    implementation("org.springframework.boot:spring-boot-starter-websocket")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.jetbrains.kotlin:kotlin-reflect")

    // 보안 관련 의존성
    implementation("org.springframework.boot:spring-boot-starter-security") // 스프링 시큐리티
    implementation("com.auth0:java-jwt:4.4.0") // jwt 토큰

    developmentOnly("org.springframework.boot:spring-boot-devtools")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

kotlin {
    compilerOptions {
        freeCompilerArgs.addAll("-Xjsr305=strict")
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}

// 프론트엔드 경로 지정
val frontendDir = "${project.projectDir}/../asset-front"

// 프론트엔드 빌드 태스크 정의 (윈도우 환경 cmd, c 추가)
val npmInstall by tasks.registering(Exec::class) {
    workingDir = file("../asset-front")
    commandLine = listOf("cmd", "/c", "npm install")
}

val npmBuild by tasks.registering(Exec::class) {
    workingDir = file("../asset-front")
    commandLine = listOf("cmd", "/c", "npm run build")
}

// 프론트엔드 빌드 후, 리소스 복사
tasks.named("processResources") {
    dependsOn(npmInstall, npmBuild)
}
