package com.test.requestvacation.service;

import com.test.requestvacation.entity.Member;
import com.test.requestvacation.repository.MemberRepository;
import com.test.requestvacation.repository.MemberVacationRepository;
import com.test.requestvacation.repository.VacationRequestsRepository;
import org.springframework.stereotype.Service;

@Service
public class MemberService {
    private MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public Member login(String email, String password) {
        return memberRepository.findByEmailAndPassword(email, password);
    }

    public Long join(Member member) {
        validateDuplicateMember(member);
        memberRepository.save(member);
        return member.getId();
    }

    private void validateDuplicateMember(Member member) {
        memberRepository.findByEmail(member.getEmail())
                .ifPresent(m -> {
                    throw new IllegalStateException("이미 존재하는 회원입니다.");
                });
    }
}
