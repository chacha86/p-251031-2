plugins {
    java
    id("org.springframework.boot") version "3.5.6"
    id("io.spring.dependency-management") version "1.1.7"
    kotlin("jvm") version "1.9.25"
    kotlin("plugin.spring") version "1.9.25"
    kotlin("plugin.jpa") version "1.9.25" // JPA 엔티티를 Kotlin에서 쉽게 사용할 수 있도록 도와줌 (자동 open, 기본 생성자 추가).
}

group = "com"
version = "0.0.1-SNAPSHOT"
description = "backend"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}

configurations {
    compileOnly {
        extendsFrom(configurations.annotationProcessor.get())
    }
}

repositories {
    mavenCentral()
}

dependencies {
    developmentOnly("org.springframework.boot:spring-boot-devtools")

    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-oauth2-client")

    implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.8.13")

    runtimeOnly("com.h2database:h2")

    compileOnly("org.projectlombok:lombok")
    annotationProcessor("org.projectlombok:lombok")

    implementation("io.jsonwebtoken:jjwt-api:0.12.6")
    runtimeOnly("io.jsonwebtoken:jjwt-impl:0.12.6")
    runtimeOnly("io.jsonwebtoken:jjwt-jackson:0.12.6")

    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.springframework.security:spring-security-test")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")


//    implementation(kotlin("stdlib-jdk8")) // kotlin("jvm") 플러그인이 kotlin-stdlib을 자동으로 의존성에 추가해 주기 때문에 필요 없음.
}

//JSR-305는 Java의 @Nullable 및 @NonNull과 같은 애너테이션을 정밀하게 다룰 수 있도록 도와주는 표준.
//strict 모드에서는 @Nullable이 붙은 Java 코드를 Kotlin에서 ?(nullable)로 정확하게 인식하고,
// @NonNull이 붙은 경우 컴파일러에서 Null Safety를 강제
kotlin {
    compilerOptions {
        freeCompilerArgs.addAll("-Xjsr305=strict")
    }
}

// @Entity, @MappedSuperclass, @Embeddable 애너테이션이 붙은 클래스는 자동으로 open 키워드를 가진 것처럼 처리.
// plugin.jpa에 내포되어 있으므로 사실상 필요 없음
allOpen {
    annotation("jakarta.persistence.Entity")
    annotation("jakarta.persistence.MappedSuperclass")
    annotation("jakarta.persistence.Embeddable")
}

tasks.withType<Test> {
    useJUnitPlatform()
}
