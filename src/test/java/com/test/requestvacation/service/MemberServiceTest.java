package com.test.requestvacation.service;

import com.test.requestvacation.entity.Member;
import com.test.requestvacation.repository.MemberRepository;
import com.test.requestvacation.repository.MemoryMemberRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

public class MemberServiceTest {

    MemberService memberService;
    MemoryMemberRepository memberRepository;

    @BeforeEach
    public void beforeEach() {
        memberRepository = new MemoryMemberRepository();
        memberService = new MemberService(memberRepository);
    }

    @Test
    void 회원가입() {
        // given
        Member member = new Member();
        member.setEmail("lhl890@naver.com");
        member.setPassword("dkagh");
        member.setName("dobot");

        // when
        Long memberId = memberService.join(member);

        // then
        Member findMember = memberService.findOne(memberId).get();
        assertThat(member.getName()).isEqualTo(findMember.getName());
    }

    @Test
    void 회원조회() {
        // given
        Long memberId = 3L;
        // when
        Member member = memberService.findOne(memberId).get();
        // then
        assertThat(member).isNull();
    }
}
