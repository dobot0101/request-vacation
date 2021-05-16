package com.test.requestvacation.controller;

import com.test.requestvacation.DTO.JoinForm;
import com.test.requestvacation.DTO.LoginForm;
import com.test.requestvacation.DTO.RequestVacationForm;
import com.test.requestvacation.entity.Member;
import com.test.requestvacation.entity.MemberVacationUsage;
import com.test.requestvacation.service.MemberService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Controller
public class MemberController {

    private MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    /**
     * 회원 가입 페이지로 이동 요청
     *
     * @return
     */
    @GetMapping("/member/join")
    public String createJoinForm() {
        return "join";
    }

    /**
     * 회원 가입 요청
     *
     * @param joinForm
     * @param model
     * @return 회원 가입 성공 시 홈페이지로 이동, 실패 시 현재 페이지 유지 및 메시지 alert
     */
    @PostMapping("/member/join")
    public ModelAndView create(JoinForm joinForm, Model model) {
        try {

            // View에서 전달한 회원가입 폼 데이터를 DB에 저장하기위해 Entity로 옮겨 담기
            Member member = new Member();
            member.setEmail(joinForm.getEmail());
            member.setName(joinForm.getName());
            member.setPassword(joinForm.getPassword());

            // 회원 가입
            memberService.join(member);

            // 회원 가입 시 회원별 당해 가용 휴가 발생 처리
            memberService.initVacationDays(member.getId());
        } catch (IllegalStateException e) {
            return new ModelAndView("join", "msg", e.getMessage());
        }
        return new ModelAndView("redirect:/");
    }

    /**
     * 로그 아웃(세션 종료)
     *
     * @param request
     * @return 홈 페이지로 이동
     */
    @RequestMapping("/member/logout")
    public String logout(HttpServletRequest request) {
        HttpSession session = request.getSession();
        session.invalidate();
        return "redirect:/";
    }

    /**
     * 로그인
     *
     * @param loginForm
     * @param model
     * @param request
     * @return 홈 페이지로 이동
     */
    @PostMapping("/member/login")
    public String login(LoginForm loginForm, Model model, HttpServletRequest request) {
        Member member = memberService.login(loginForm.getEmail(), loginForm.getPassword());
        if (member == null) {
            model.addAttribute("msg", "로그인에 실패했습니다. 로그인 정보를 확인해주세요.");
            return "home";
        } else {
            HttpSession session = request.getSession();
            session.setAttribute("memberName", member.getName());
            session.setAttribute("memberId", member.getId());

            return "home";
        }
    }

    /**
     * 휴가 신청 페이지 요청
     *
     * @param request
     * @param model
     * @return 요청 성공 시 휴가 신청 페이지로 이동, 실패 시 홈 페이지로 이동
     */
    @GetMapping("/member/vacationRequest")
    public String createForm(HttpServletRequest request, Model model) {
        HttpSession session = request.getSession();
        long memberId = (long) session.getAttribute("memberId");

        // 휴가 가능일수 0 이하이면 홈 페이지로 이동
        BigDecimal remainVacationDays = memberService.getRemainVacationDays(memberId);
        if (remainVacationDays.compareTo(new BigDecimal(0)) <= 0) {
            model.addAttribute("msg", "남은 휴가가 없습니다.");
            return "home";
        }

        model.addAttribute("remainVacationDays", remainVacationDays);

        return "vacationRequest";
    }

    /**
     * 휴가 신청(생성)
     *
     * @param requestVacationForm
     * @param request
     * @return 휴가 내역으로 redirect
     */
    @PostMapping("/member/createVacation")
    public String create(RequestVacationForm requestVacationForm, HttpServletRequest request) {

        // 세션의 로그인 아이디 확인
        HttpSession session = request.getSession();
        long memberId = (long) session.getAttribute("memberId");

        // 휴가 신청 Form 으로 전달받은 값들을 DB에 저장하기 위해 Entity에 담기
        MemberVacationUsage memberVacationUsage = new MemberVacationUsage();
        memberVacationUsage.setMemberId(memberId);
        memberVacationUsage.setComments(requestVacationForm.getComment());
        memberVacationUsage.setUseDay(new BigDecimal(requestVacationForm.getVacationType()));


        // 휴가시작일, 종료일을 Date 타입으로 변경하여 Entity에 담기
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date startDate = null;
        Date endDate = null;

        try {
            if (requestVacationForm.getStartDate() != null) {
                startDate = dateFormat.parse(requestVacationForm.getStartDate());
            }

            if (requestVacationForm.getEndDate() != null) {
                endDate = dateFormat.parse(requestVacationForm.getEndDate());
            }

        } catch (ParseException e) {
            e.printStackTrace();
        }

        if (startDate != null) {
            memberVacationUsage.setStartAt(startDate);
        }

        if (endDate != null) {
            memberVacationUsage.setEndAt(endDate);
        }

        // 휴가 신청(생성)
        memberService.createVacation(memberVacationUsage);

        return "redirect:/member/vacationList";
    }


    /**
     * 휴가 내역 조회
     *
     * @param model
     * @param request
     * @return 휴가 내역 View
     */
    @GetMapping("/member/vacationList")
    public String list(String msg, Model model, HttpServletRequest request) {
        HttpSession session = request.getSession();
        long memberId = (long) session.getAttribute("memberId");

        List<MemberVacationUsage> list = memberService.findVacationRequests(memberId);
        model.addAttribute("vacationList", list);

        if (msg != null) {
            model.addAttribute("msg", msg);
        }

        return "vacationList";
    }

    /**
     * 휴가 취소
     *
     * @param vacationId
     * @return 휴가 내역 페이지로 리다이렉트
     */
    @PostMapping("/member/cancelVacation")
    public ModelAndView cancel(Long vacationId) {

        ModelAndView mv = new ModelAndView();
        mv.setViewName("redirect:/member/vacationList");

//        url 뒤에 attribute 표시되지 않게 하려면 아래 코드 사용
//        RedirectView rv = new RedirectView("/member/vacationList");
//        rv.setExposeModelAttributes(false);
//        mv.setView(rv);

        if (!memberService.cancelVacation(vacationId)) {
            mv.addObject("msg", "이미 시작되어 취소할 수 없습니다.");
        }

        return mv;
    }
}
