# example-api

- [Jackson2ObjectMapperBuilderCustomizer](#jackson2objectmapperbuildercustomizer)
- [LogBack](#logback)
- [Exception 처리](#exception-처리)
- [Masking](#Masking)
- [JPA](#JPA)

## TODO
1. ~~DTO Serialize/Deserialize 확인~~
2. ~~DTO ToString~~
3. ~~로깅 시, masking처리(annotation, logback masking 등)~~
4. Sample CRUD
5. 시작 시, 필요 데이터 로딩(H2)
6. 화면 메시지 처리(DB로 관리 가능하도록, Redis 활용하여 캐싱)
7. restdoc, swagger plugin
8. junit5 테스트 코드 샘플
9. redis 등 필요한 항목 docker로 올릴 수 있도록 docker-compose 파일
10. Dockerfile
11. github ci/cd pipeline


## Jackson2ObjectMapperBuilderCustomizer
- Serialization
  |구분|제외여부|
  |---|---|
  |null|제외|
  |Collection|isEmpty == true인 경우 제외|
  |Array|size() == 0인 경우 제외|
  |String|length == 0 인 경우 제외|

- 날짜 및 시간 Serialization & Deserialization : [JavaTimeModule](https://fasterxml.github.io/jackson-modules-java8/javadoc/datetime/2.9/com/fasterxml/jackson/datatype/jsr310/JavaTimeModule.html)

## LogBack
> 아래 내용들은 logback-spring.xml에 직접 설정해도 되지만 application.yml에서 전부 관리하는게 편하지 않을까 하는 생각에 정리
- ConsoleAppender  
  |application.yml|logback-spring.xml|설명|Default 값|
  |---|---|:---|:---|
  |logging.pattern.console|CONSOLE_LOG_PATTERN|콘솔에서 사용할 로그 패턴|_${LOG_DATEFORMAT_PATTERN}_ _${LOG*LEVEL_PATTERN}_ ${PID} - [%t] %logger{20} : %m%n|
  |logging.pattern.dateformat|LOG_DATEFORMAT_PATTERN|로그 날짜 포맷에 사용할 Appender 패턴|yyyy-MM-dd'T'HH:mm:ss.SSS|
  |logging.pattern.level|LOG_LEVEL_PATTERN|로그 레벨을 렌더링할 때 사용할 포맷|%.-1p|
  |PID|PID|현재 프로세스 ID||
  |logging.exception-conversion-word|LOG_EXCEPTION_CONVERSION_WORD|예외를 로킹할 떄 사용할 conversionword|%wEx|
- FileAppender  
  |application.yml|logback-spring.xml|설명|Default 값|
  |---|---|:---|:---|
  |logging.pattern.file|FILE_LOG_PATTERN|파일에서 사용할 로그 패턴|_${LOG_DATEFORMAT_PATTERN}_ _${LOG_LEVEL_PATTERN}_ ${PID} - [%t] %logger{20} : %m%n|
  |logging.pattern.dateformat|LOG_DATEFORMAT_PATTERN|로그 날짜 포맷에 사용할 Appender 패턴|yyyy-MM-dd'T'HH:mm:ss.SSS|
  |logging.pattern.level|LOG_LEVEL_PATTERN|로그 레벨을 렌더링할 때 사용할 포맷|%.-1p|
  |PID|PID|현재 프로세스 ID||
  |logging.exception-conversion-word|LOG_EXCEPTION_CONVERSION_WORD|예외를 로킹할 떄 사용할 conversionword|%wEx|
  |logging.charset.file|FILE_LOG_CHARSET|파일 logging에 사용할 캐릭터 셋|UTF-8|
  |logging.file.name|LOG_FILE|로그 파일 명|${LOG_PATH}/spring.log|
  |logging.file.path|LOG_PATH|로그 파일 Path|/tmp/logs|
  |logging.logback.rollingpolicy.file-name-pattern|LOGBACK_ROLLINGPOLICY_FILE_NAME_PATTERN|로그 아카이브 만들 때 파일 이름에 사용할 패턴|${LOG_FILE}.%d{yyyy-MM-dd}.%i.gz|
  |logging.logback.rollingpolicy.clean-history-on-start|LOGBACK_ROLLINGPOLICY_CLEAN_HISTORY_ON_START|Application 시작할 때 로그 아카이브를 비워야 하는지 여부|false|
  |logging.logback.rollingpolicy.max-file-size|LOGBACK_ROLLINGPOLICY_MAX_FILE_SIZE|로그 파일을 아카이빙하기 전 최대 사이즈|10MB|
  |logging.logback.rollingpolicy.total-size-cap|LOGBACK_ROLLINGPOLICY_TOTAL_SIZE_CAP|로그 아카이브 파일들의 최대 용량|0|
  |logging.logback.rollingpolicy.max-history|LOGBACK_ROLLINGPOLICY_MAX_HISTORY|로그 아카이브를 유지할 일수|7|

## Exception 처리
> ErrorResponse Format 또는 `GlobalExceptionHandler`에 정의되어 있는 예외처리는 모두 상황에 맞게 바꿔서 사용 필요.

### Throw NoHandlerFoundException 설정

- 404 에러 발생 시, 기본 whitelabel 에러페이지 대신 NoHandlerFoundException을 throw하여 처리하도록 설정
  ```yml
  spring:
    mvc:
      # dispatcherServlet에서 알맞은 handler를 찾을 수 없을 때 NoHandlerFoundExcpetion throw
      throw-exception-if-no-handler-found: true
    web:
      resources:
        add-mappings: false # 기본 resouce handler disabled
  ```

### ErrorResponse Format
> 에러의 응답형태는 항상 일정하도록 Class를 선언한다. `ErrorResponse.java` 참조
- Json형식으로 Return되며 Format은 아래와 같다.
  ```json
  {
    "errorCode": "error-000001",
    "errorMessage": "Unkwon Error",
    "meesage": "화면에 보이는 메세지",
    "data": {
      "name": "test",
      "age": "99"
    },
    "errors": [
      {}
    ]
  }
  ```
- ErrorResponse 속성
  | Type | Name | Required | Description |
  | ------------ | ------------ | -------- | :------------------------------- |
  | String | errorCode | Y | 에러코드 |
  | String | errorMessage | Y | 에러메시지 |
  | String | message | N | 화면에 보여줄 메시지 |
  | Object | data | N | 에러발생 시 전달한 데이터 |
  | List<String> | errors | N | `@Valid`로 검증실패 한 에러 목록 |

### Exception처리 클래스 : GlobalExceptionHandler(@RestControllerAdvice)
- `@RestControllerAdvice`을 적용하여 Exception을 처리
- `ResponseEntityExceptionHandler`를 상속받아서 기본적인 Spring MVC 예외처리.
  > `GlobalExceptionHandler`에 구현되어 있는 에러처리 소스는 `ResponseEntityExceptionHandler` 그대로 옮겨서 overriding함. 필요 시 수정하여 사용
- 현재 처리 중인 Exception 목록
  1. [`ResponseEntityExceptionHandler`](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/servlet/mvc/method/annotation/ResponseEntityExceptionHandler.html) : 참조
  2. `ApiException.java` : HttpStatus default 값은 500, 다른 status로 변경 가능
  3. `BadRequestException.java` : HttpStatus 400 으로 고정한 exception
  4. `NotFoundException.java` : HttpStatus 404 으로 고정한 exception

### ErrorCode 정의
- `BaseErrorCode` interface를 implements 하여 enum 클래스로 정의(참조 : `GlobalErrorCode`)
- enum 사용하는 이유(일단 이렇게 생각...)
  1. 자동완성/오타 등 IDE 지원을 받을 수 있음.
  2. ErrorCode의 허용 가능한 값을 제어할 수 있음
  3. 코드 변경 시, 변경 범위를 최소할 수 있음.

## Masking
1. BaseDto 상속 필요
   - ToString을 제어하여 지정한 Field를 출력하지 않음
   - JSON 형태로 출력 : `org.apache.commons.lang3.builder.ToStringStyle` 참조
2. Annotation 생성 
   - `@MaskingField` : masking하려는 field 위에 선언 
3. ToString
   - apache common lang3 라이브러리 추가
   - `ReflectionToStringBuilder`를 상속받아서 `MaskingToStringBuilder` 구현
   - `@MaskingField`가 붙은 field는 출력하지 않음
4. Logback도 ToString으로 Object 출력하는 듯??
   ```java
    ExampleDto dto = new ExampleDto();
    dto.setAge(1);
    dto.setLongData(5L);
    dto.setTestNm("testNm"); // @MaskingField 선언

    log.info(dto.toString()); // {"testNm":"******","age":1,"longData":5}
    log.info("test : {}", dto); // test : {"testNm":"******","age":1,"longData":5}
    ```
   
## Util
- com.example.api.util 
  - FileUtil : 파일
- com.google.common.base(Guava)
  - [String 관련 Util](https://github.com/google/guava/wiki/StringsExplained)
    - [Strings](https://guava.dev/releases/snapshot/api/docs/com/google/common/base/Strings.html)
    - [Joiner](https://guava.dev/releases/snapshot/api/docs/com/google/common/base/Joiner.html)
    - [Splitter](https://guava.dev/releases/snapshot/api/docs/com/google/common/base/Splitter.html)
    - [CharMatcher](https://guava.dev/releases/snapshot/api/docs/com/google/common/base/CharMatcher.html)
    - [Charsets](https://guava.dev/releases/snapshot/api/docs/com/google/common/base/Charsets.html)
    - [CaseFormat](https://guava.dev/releases/snapshot/api/docs/com/google/common/base/CaseFormat.html)
  - 그 외 Util은 [Guava 참조](https://github.com/google/guava/wiki)


## JPA
- 학습 필요
  - hibornate vs spring data jpa 차이점
  - jpa persistence
  - JPA 동작 구조
  - 복잡한 쿼리 보조 : queryDsl?
