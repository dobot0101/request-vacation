package com.test.requestvacation.service;

import com.test.requestvacation.common.HolidayUtil;
import com.test.requestvacation.entity.Member;
import com.test.requestvacation.entity.MemberVacation;
import com.test.requestvacation.entity.VacationRequest;
import com.test.requestvacation.entity.VacationRequestsGroupBy;
import com.test.requestvacation.repository.MemberRepository;
import com.test.requestvacation.repository.MemberVacationRepository;
import com.test.requestvacation.repository.VacationRequestRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class MemberService {
    private MemberRepository memberRepository;
    private MemberVacationRepository memberVacationRepository;
    private VacationRequestRepository vacationRequestRepository;


    public MemberService(MemberRepository memberRepository, MemberVacationRepository memberVacationRepository, VacationRequestRepository vacationRequestRepository) {
        this.memberRepository = memberRepository;
        this.memberVacationRepository = memberVacationRepository;
        this.vacationRequestRepository = vacationRequestRepository;
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


    /**
     * 남은 휴가일수 구하기
     *
     * @param memberId
     * @return
     */
    public int getRemainVacationDays(Long memberId) {
        MemberVacation memberVacation = memberVacationRepository.findByMemberId(memberId);
        int vacationDays = memberVacation.getVacationDays();

        int useVacationDays = 0;
        VacationRequestsGroupBy vacationRequestsGroupBy = vacationRequestRepository.getTotalUsedVacationDays(memberId);
        if (vacationRequestsGroupBy != null) {
            useVacationDays = vacationRequestsGroupBy.getTotalUseDays();
        }

        return vacationDays - useVacationDays;
    }

    /**
     * 회원가입 시 휴가 15일 초기화
     *
     * @param memberId
     */
    public void initVacationDays(Long memberId) {
        MemberVacation memberVacation = new MemberVacation();
        memberVacation.setMemberId(memberId);

        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);

        memberVacation.setYear(year);
        memberVacation.setVacationDays(15);
        memberVacationRepository.save(memberVacation);
    }

    /**
     * 휴가 내역 조회
     *
     * @param memberId
     * @return
     */
    public List<VacationRequest> findVacationRequests(Long memberId) {
        return vacationRequestRepository.findAllByMemberId(memberId);
    }

    public String createVacation(VacationRequest vacationRequest) {
        // 공휴일 확인 및 휴가 사용일자 계산
        BigDecimal useDay = vacationRequest.getUseDay();
        if (useDay.equals(1)) {
            useDay = new BigDecimal(getUseDayCountByTerm(vacationRequest));
            vacationRequest.setUseDay(useDay);
        }

        return "";
    }

    private int getUseDayCountByTerm(VacationRequest vacationRequest) {

        int useDayCount = 0;

        Date startDate = vacationRequest.getStartAt();
        Date endDate = vacationRequest.getEndAt();

        if (startDate != null && endDate != null) {
            DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");

            Date newStartDate = null;
            Date newEndDate = null;

            try {
                newStartDate = dateFormat.parse(startDate.toString());
                newEndDate = dateFormat.parse(endDate.toString());
            } catch (ParseException e) {
                e.printStackTrace();
            }

            if (newStartDate != null && newEndDate != null) {
                Calendar calendar1 = Calendar.getInstance();
                Calendar calendar2 = Calendar.getInstance();

                calendar1.setTime(newStartDate);
                calendar2.setTime(newEndDate);

                while (calendar1.compareTo(calendar2) != 1) {
                    System.out.printf("%tF\n", calendar1.getTime());
                    if (!HolidayUtil.isWeekendOrHoliday(calendar1.getTime())) {
                        useDayCount++;
                    }
                    calendar1.add(Calendar.DATE, 1);
                }
            }
        }

        return useDayCount;
    }


    /**
     * 휴가 취소
     *
     * @param vacationId
     * @return
     */
    public Long cancelVacation(Long vacationId) {

        VacationRequest vacationRequest = vacationRequestRepository.findById(vacationId).get();
        vacationRequest.setIsCancel("Y");
        vacationRequestRepository.save(vacationRequest);
        return vacationId;
    }
}
