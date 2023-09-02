package com.example.api.member.service;

import static com.example.api.code.MemberErrorCode.MEMBER_NOT_FOUND;

import com.example.api.global.exception.NotFoundException;
import com.example.api.member.domain.Member;
import com.example.api.member.dto.MemberRequest;
import com.example.api.member.mapper.MemberMapper;
import com.example.api.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class MemberService {

    private final MemberRepository memberRepository;
    private final MemberMapper memberMapper;

    @Transactional(readOnly = true)
    public Member findMemberById(Long id) {
        return memberRepository.findById(id)
            .orElseThrow(() -> new NotFoundException(MEMBER_NOT_FOUND));
    }

    @Transactional
    public Member insert(MemberRequest memberRequest) {
        return memberRepository.save(memberMapper.memberRequestToMember(memberRequest));
    }

    @Transactional
    public Member update(Long id, MemberRequest memberRequest) {
        memberRepository.findById(id).orElseThrow(() -> new NotFoundException(MEMBER_NOT_FOUND));
        return memberRepository.save(memberMapper.memberRequestToMember(memberRequest));
    }
}
