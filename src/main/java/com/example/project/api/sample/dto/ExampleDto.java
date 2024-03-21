package com.example.project.api.sample.dto;

import com.example.project.global.dto.BaseDto;
import com.example.project.global.annotation.masking.MaskingField;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class ExampleDto extends BaseDto {

    @MaskingField
    private String testNm;
    @MaskingField
    private int age;
    @MaskingField
    private long longData;

    private String content;

    @Builder
    public ExampleDto(String testNm, int age, long longData, String content) {
        this.testNm = testNm;
        this.age = age;
        this.longData = longData;
        this.content = content;
    }
}
