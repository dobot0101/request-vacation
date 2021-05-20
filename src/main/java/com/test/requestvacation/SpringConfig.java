package com.test.requestvacation;

import com.test.requestvacation.repository.MemberRepository;
import com.test.requestvacation.repository.MemberVacationRepository;
import com.test.requestvacation.repository.MemberVacationUsageRepository;
import com.test.requestvacation.service.MemberService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringConfig {
    private final MemberRepository memberRepository;
    private final MemberVacationUsageRepository memberVacationUsageRepository;
    private final MemberVacationRepository memberVacationRepository;

    public SpringConfig(MemberRepository memberRepository, MemberVacationUsageRepository memberVacationUsageRepository, MemberVacationRepository memberVacationRepository) {
        this.memberRepository = memberRepository;
        this.memberVacationUsageRepository = memberVacationUsageRepository;
        this.memberVacationRepository = memberVacationRepository;
    }

    @Bean
    public MemberService memberService() {
        return new MemberService(memberRepository, memberVacationRepository, memberVacationUsageRepository);
    }
}
