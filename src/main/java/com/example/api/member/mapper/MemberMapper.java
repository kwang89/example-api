package com.example.api.member.mapper;

import org.mapstruct.Mapper;

import com.example.api.member.domain.Member;
import com.example.api.member.dto.MemberRequest;

@Mapper
public interface MemberMapper {
	Member memberRequestToMember(MemberRequest memberRequest);
}
