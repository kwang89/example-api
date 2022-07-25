# example-api

* [Jackson2ObjectMapperBuilderCustomizer](#jackson2objectmapperbuildercustomizer)
* [LogBack](#logback)

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
- ConsoleAppender
- FileAppender
