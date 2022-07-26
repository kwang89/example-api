# example-api

* [Jackson2ObjectMapperBuilderCustomizer](#jackson2objectmapperbuildercustomizer)
* [LogBack](#logback)

## Jackson2ObjectMapperBuilderCustomizer

* Serialization
  |구분|제외여부|
  |---|---|
  |null|제외|
  |Collection|isEmpty == true인 경우 제외|
  |Array|size() == 0인 경우 제외|
  |String|length == 0 인 경우 제외|

* 날짜 및 시간 Serialization & Deserialization : [JavaTimeModule](https://fasterxml.github.io/jackson-modules-java8/javadoc/datetime/2.9/com/fasterxml/jackson/datatype/jsr310/JavaTimeModule.html)

## LogBack

> 아래 내용들은 logback-spring.xml에 직접 설정해도 되지만 application.yml에서 전부 관리하는게 편하지 않을까 하는 생각에 정리

* ConsoleAppender  
  |application.yml|logback-spring.xml|설명|Default 값|
  |---|---|:---|:---|
  |logging.pattern.console|CONSOLE_LOG_PATTERN|콘솔에서 사용할 로그 패턴|_${LOG_DATEFORMAT_PATTERN}_ _${LOG_LEVEL_PATTERN}_ ${PID} - [%t] %logger{20} : %m%n|
  |logging.pattern.dateformat|LOG_DATEFORMAT_PATTERN|로그 날짜 포맷에 사용할 Appender 패턴|yyyy-MM-dd'T'HH:mm:ss.SSS|
  |logging.pattern.level|LOG_LEVEL_PATTERN|로그 레벨을 렌더링할 때 사용할 포맷|%.-1p|
  |PID|PID|현재 프로세스 ID||
  |logging.exception-conversion-word|LOG_EXCEPTION_CONVERSION_WORD|예외를 로킹할 떄 사용할 conversionword|%wEx|
* FileAppender  
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
