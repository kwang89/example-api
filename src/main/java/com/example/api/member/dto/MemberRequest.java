package com.example.api.member.dto;

import com.example.api.sample.dto.ExampleDto;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class MemberRequest extends ExampleDto {

    private Long id;
    private String name;
    private String phoneNo;

    @Builder
    public MemberRequest(Long id, String name, String phoneNo) {
        this.id = id;
        this.name = name;
        this.phoneNo = phoneNo;
    }
}
