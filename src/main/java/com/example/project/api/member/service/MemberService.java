package com.example.project.api.member.service;

import static com.example.project.code.MemberErrorCode.MEMBER_NOT_FOUND;

import com.example.project.global.exception.NotFoundException;
import com.example.project.api.member.domain.Member;
import com.example.project.api.member.dto.MemberRequest;
import com.example.project.api.member.mapper.MemberMapper;
import com.example.project.api.member.repository.MemberRepository;
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
