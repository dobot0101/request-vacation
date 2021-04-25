package com.test.requestvacation.service;

import com.test.requestvacation.entity.MemberVacation;
import com.test.requestvacation.repository.MemberVacationRepository;
import com.test.requestvacation.repository.VacationRequestsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MemberVacationService {

//    @Autowired
    MemberVacationRepository memberVacationRepository;

//    @Autowired
    VacationRequestsRepository vacationRequestsRepository;

    public MemberVacationService(MemberVacationRepository memberVacationRepository, VacationRequestsRepository vacationRequestsRepository) {
        this.memberVacationRepository = memberVacationRepository;
        this.vacationRequestsRepository = vacationRequestsRepository;
    }

    public Integer getRemainVacationDays(Long memberId) {
        int vacationDays = memberVacationRepository.findByMemberId(memberId);
        int useVacationDays = vacationRequestsRepository.getTotalUsedVacationDays(memberId);

        return vacationDays - useVacationDays;
    }

    public void initVacationDays(Long memberId) {
        MemberVacation memberVacation = new MemberVacation();
        memberVacation.setMemberId(memberId);
        memberVacation.setYear(2021);
        memberVacation.setVacationDays(15);
        memberVacationRepository.save(memberVacation);
    }
}
