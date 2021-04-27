package com.test.requestvacation.service;

import com.test.requestvacation.entity.MemberVacation;
import com.test.requestvacation.entity.VacationRequests;
import com.test.requestvacation.entity.VacationRequestsGroupBy;
import com.test.requestvacation.repository.MemberVacationRepository;
import com.test.requestvacation.repository.VacationRequestsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.List;

@Service
public class MemberVacationService {

    MemberVacationRepository memberVacationRepository;

    VacationRequestsRepository vacationRequestsRepository;

    public MemberVacationService(MemberVacationRepository memberVacationRepository, VacationRequestsRepository vacationRequestsRepository) {
        this.memberVacationRepository = memberVacationRepository;
        this.vacationRequestsRepository = vacationRequestsRepository;
    }

    public Integer getRemainVacationDays(Long memberId) {
        MemberVacation memberVacation = memberVacationRepository.findByMemberId(memberId);
        int vacationDays = memberVacation.getVacationDays();

        int useVacationDays = 0;
        VacationRequestsGroupBy vacationRequestsGroupBy = vacationRequestsRepository.getTotalUsedVacationDays(memberId);
        if (vacationRequestsGroupBy != null) {
            useVacationDays = vacationRequestsGroupBy.getTotalUseDays();
        }

        return vacationDays - useVacationDays;
    }

    public void insertVacationDays(Long memberId) {
        MemberVacation memberVacation = new MemberVacation();
        memberVacation.setMemberId(memberId);

        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);

        memberVacation.setYear(year);
        memberVacation.setVacationDays(15);
        memberVacationRepository.save(memberVacation);
    }
}
