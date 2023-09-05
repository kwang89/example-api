package com.example.project.api.member.mapper;

import com.example.project.api.member.domain.Member;
import com.example.project.api.member.dto.MemberRequest;
import org.mapstruct.Mapper;

@Mapper
public interface MemberMapper {

    Member memberRequestToMember(MemberRequest memberRequest);
}
