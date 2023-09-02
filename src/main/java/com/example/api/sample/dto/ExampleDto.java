package com.example.api.sample.dto;

import com.example.api.global.dto.BaseDto;
import com.example.api.global.masking.MaskingField;
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
}
