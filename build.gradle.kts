plugins {
    id("org.springframework.boot") version "3.4.2"
    id("io.spring.dependency-management") version "1.1.7"
    id("org.asciidoctor.jvm.convert") version "4.0.4"
    id("com.epages.restdocs-api-spec") version "0.19.4"
    id("org.ec4j.editorconfig") version "0.1.0"
    java
//    id "checkstyle"
}

group = "com.example"
version = "0.0.1-SNAPSHOT"

java {
    sourceCompatibility = JavaVersion.VERSION_21
}

configurations {
    compileOnly {
        extendsFrom(configurations.annotationProcessor.get())
    }
}

repositories {
    mavenCentral()
}


val asciidoctorExt by configurations.creating
var snippetsDir by extra(file("build/generated-snippets"))

dependencies {

    val mapstructVersion = "1.6.3"
    val guavaVersion = "33.4.0-jre"
    val apacheTextVersion = "1.13.0"
    val apacheCollectionsVersion = "4.4"
    val apacheLangVersion = "3.17.0"
    val apacheIoVersion = "2.18.0"
    val epagesVersion = "0.19.4"

    // Spring
    // implementation "org.springframework.boot:spring-boot-starter-security"
    // testImplementation "org.springframework.security:spring-security-test"
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")
    developmentOnly("org.springframework.boot:spring-boot-devtools")
    testImplementation("org.springframework.boot:spring-boot-starter-test")

    // restdocs
    testImplementation("org.springframework.restdocs:spring-restdocs-mockmvc")
    asciidoctorExt("org.springframework.restdocs:spring-restdocs-asciidoctor")
    testImplementation("com.epages:restdocs-api-spec-mockmvc:$epagesVersion")

    // MapStruct
    implementation("org.mapstruct:mapstruct:$mapstructVersion")
    annotationProcessor("org.mapstruct:mapstruct-processor:$mapstructVersion")
    testAnnotationProcessor("org.mapstruct:mapstruct-processor:$mapstructVersion")

    // guava
    implementation("com.google.guava:guava:$guavaVersion")
    // apache common
    implementation("org.apache.commons:commons-text:$apacheTextVersion")
    implementation("org.apache.commons:commons-collections4:$apacheCollectionsVersion")
    implementation("org.apache.commons:commons-lang3:$apacheLangVersion")
    implementation("commons-io:commons-io:$apacheIoVersion")
    // lombok
    compileOnly("org.projectlombok:lombok")
    annotationProcessor("org.projectlombok:lombok")
    // H2
    runtimeOnly("com.h2database:h2")
}

tasks {
    compileJava {
        options.encoding = "UTF-8"
        options.compilerArgs.add("-Amapstruct.defaultComponentModel=spring")
    }

    compileTestJava {
        options.encoding = "UTF-8"
    }

    test {
        useJUnitPlatform()
        outputs.dir(snippetsDir)
    }

    // build 시, asciidoctor task 실행
    build {
        dependsOn("asciidoctor")
    }

    // https://asciidoctor.github.io/asciidoctor-gradle-plugin/master/user-guide/
    asciidoctor {
        inputs.dir(snippetsDir)
        baseDirFollowsSourceFile()
        configurations(asciidoctorExt.name)
        dependsOn(test)

        // asciidoc 생성 후, swagger 및 postman 생성
        finalizedBy("openapi", "openapi3", "postman")
    }

    openapi { //2.3
        host = "localhost:8080"
        title = "My API"
        description = "My API description"
        version = "1.0.0"
        format = "json"
    }

    // 서버 여러개 설정 시 참조
    // https://github.com/ePages-de/restdocs-api-spec#gradle-plugin-configuration
    // https://github.com/OAI/OpenAPI-Specification/blob/main/versions/3.0.1.md#server-object
    openapi3 {
        setServer("http://localhos:8080")
        title = "My API"
        description = "My API description"
        version = "0.1.0"
        format = "yaml"
    }

    postman {
        title = "My API"
        version = "0.1.0"
        baseUrl = "https://localhost:8080"
    }

    editorconfig {
        includes = listOf("src/**", "build.gradle.kts")
        excludes = listOf("build")
    }

    check {
        dependsOn(editorconfigCheck)
    }
}
//checkstyle {
//    maxWarnings = 0
//    configFile = file("${rootDir}/naver-checkstyle-rules.xml")
//    configProperties = ["suppressionFile": "${rootDir}/naver-checkstyle-suppressions.xml"]
//    toolVersion = "8.24"
//}







