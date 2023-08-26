plugins {
    id("org.springframework.boot") version "3.1.3"
    id("io.spring.dependency-management") version "1.1.3"
    id("org.asciidoctor.jvm.convert") version "3.3.2"
    id("com.epages.restdocs-api-spec") version "0.18.2"
    id("org.ec4j.editorconfig") version "0.0.3"
    java
//    id "checkstyle"
}

group = "com.example"
version = "0.0.1-SNAPSHOT"

java {
    sourceCompatibility = JavaVersion.VERSION_17
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

    val mapstructVersion = "1.5.5.Final"
    val guavaVersion = "32.1.2-jre"
    val commonTextVersion = "1.10.0"
    val epagesVersion = "0.18.2"

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
    testImplementation ("com.epages:restdocs-api-spec-mockmvc:$epagesVersion")

    // MapStruct
    implementation ("org.mapstruct:mapstruct:$mapstructVersion")
    annotationProcessor ("org.mapstruct:mapstruct-processor:$mapstructVersion")
    testAnnotationProcessor ("org.mapstruct:mapstruct-processor:$mapstructVersion")

    // guava
    implementation ("com.google.guava:guava:$guavaVersion")
    // apache common text
    implementation ("org.apache.commons:commons-text:$commonTextVersion")
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

    withType<Test> {
        useJUnitPlatform()
    }

    test {
        outputs.dir(snippetsDir)
    }

    // build 시, asciidoctor task 실행
    build {
        dependsOn("asciidoctor")
    }

    asciidoctor {
        inputs.dir(snippetsDir)
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







