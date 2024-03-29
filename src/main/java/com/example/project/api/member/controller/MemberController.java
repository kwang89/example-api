package com.example.project.api.member.controller;

import com.example.project.api.member.domain.Member;
import com.example.project.api.member.dto.MemberRequest;
import com.example.project.api.member.service.MemberService;
import java.net.URI;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/member/{id}")
    public Member findById(@PathVariable("id") Long id) {
        return memberService.findMemberById(id);
    }

    @PostMapping("/member")
    public ResponseEntity<Member> insert(@RequestBody MemberRequest memberRequest) {
        Member member = memberService.insert(memberRequest);
        return ResponseEntity.created(URI.create("/member/" + member.getId())).body(member);
    }

    @PutMapping("/member/{id}")
    public Member update(@PathVariable("id") Long id, @RequestBody MemberRequest memberRequest) {
        return memberService.update(id, memberRequest);
    }
}
