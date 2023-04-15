package com.example.api.member.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.api.member.domain.Member;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {

}
