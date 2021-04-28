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
import org.springframework.web.servlet.ModelAndView;

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

    /**
     * 로그인
     *
     * @param email
     * @param password
     * @return
     */
    public Member login(String email, String password) {
        return memberRepository.findByEmailAndPassword(email, password);
    }


    /**
     * 회원 가입
     *
     * @param member
     * @return
     */
    public Long join(Member member) {
        validateDuplicateMember(member);
        memberRepository.save(member);
        return member.getId();
    }

    /**
     * 회원 가입 시 중복 확인
     *
     * @param member
     */
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
    public BigDecimal getRemainVacationDays(Long memberId) {
        MemberVacation memberVacation = memberVacationRepository.findByMemberId(memberId);

        BigDecimal vacationDays = memberVacation.getVacationDays();
        BigDecimal useVacationDays = new BigDecimal(0);

        List<VacationRequest> vacationRequestList = vacationRequestRepository.findAllByMemberId(memberId);
        for (VacationRequest vacationRequest : vacationRequestList) {
            useVacationDays = useVacationDays.add(vacationRequest.getUseDay());
        }

        return vacationDays.subtract(useVacationDays);
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
        memberVacation.setVacationDays(new BigDecimal(15));
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

    /**
     * 휴가 신청(저장)
     *
     * @param vacationRequest
     * @return
     */
    public long createVacation(VacationRequest vacationRequest) {

        // 연차 사용이면 시작일, 종료일 기준으로 실제 휴가 사용 개수 구함
        BigDecimal useDay = vacationRequest.getUseDay();
        if (useDay.compareTo(new BigDecimal(1)) == 0) {
            useDay = new BigDecimal(getUseDayCountByTerm(vacationRequest));
            vacationRequest.setUseDay(useDay);
        }

        vacationRequestRepository.save(vacationRequest);

        return vacationRequest.getId();
    }

    /**
     * 휴가 기간 중 실제 차감되는 휴가 사용 개수 구하기
     *
     * @param vacationRequest
     * @return
     */
    private int getUseDayCountByTerm(VacationRequest vacationRequest) {

        int useDayCount = 0;

        Date startDate = vacationRequest.getStartAt();
        Date endDate = vacationRequest.getEndAt();

        if (startDate != null && endDate != null) {
            Calendar calendar1 = Calendar.getInstance();
            Calendar calendar2 = Calendar.getInstance();

            calendar1.setTime(startDate);
            calendar2.setTime(endDate);

            // 휴가 시작일, 종료일 사이의 날짜를을 하나씩 공휴일, 주말 여부 확인하여 실제 휴가 사용 개수 구함
            while (calendar1.compareTo(calendar2) != 1) {
                if (!HolidayUtil.isWeekendOrHoliday(calendar1.getTime())) {
                    useDayCount++;
                }
                calendar1.add(Calendar.DATE, 1);
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
    public boolean cancelVacation(Long vacationId) {

        VacationRequest vacationRequest = vacationRequestRepository.findById(vacationId).get();

        Date startDate = vacationRequest.getStartAt();
        Date date = new Date();

        if (startDate.compareTo(date) > 0) {
            vacationRequest.setIsCancel("Y");
            vacationRequestRepository.save(vacationRequest);
        } else {
            return false;
        }

        return true;
    }


}
