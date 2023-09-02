package com.example.api.member.mapper;

import com.example.api.member.domain.Member;
import com.example.api.member.dto.MemberRequest;
import org.mapstruct.Mapper;

@Mapper
public interface MemberMapper {

    Member memberRequestToMember(MemberRequest memberRequest);
}
