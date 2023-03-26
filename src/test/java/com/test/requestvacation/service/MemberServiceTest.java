package com.test.requestvacation.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.test.requestvacation.entity.Member;
import com.test.requestvacation.repository.MemoryMemberRepository;

public class MemberServiceTest {
    @Autowired
    MemberService memberService;

    @Autowired
    MemoryMemberRepository memberRepository;

    @BeforeEach
    public void beforeEach() {
        memberRepository = new MemoryMemberRepository();
        memberService = new MemberService(memberRepository);
    }

    @Test
    void 회원가입() {
        Member member = new Member();
        member.setEmail("test@test.com");
        member.setPassword("test");
        member.setName("dobot");

        Long memberId = memberService.join(member);

        Member findMember = memberService.findOne(memberId).get();
        assertThat(member.getName()).isEqualTo(findMember.getName());
    }

    @Test
    void 회원조회() {
        Long memberId = 3L;

        Optional<Member> member = memberService.findOne(memberId);

        assertThat(member).isEmpty();
    }
}
