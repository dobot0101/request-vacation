package com.test.requestvacation.controller;

import com.test.requestvacation.DTO.JoinForm;
import com.test.requestvacation.DTO.LoginForm;
import com.test.requestvacation.DTO.RequestVacationForm;
import com.test.requestvacation.entity.Member;
import com.test.requestvacation.entity.VacationRequest;
import com.test.requestvacation.service.MemberService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
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

    @GetMapping("/member/join")
    public String createJoinForm() {
        return "join";
    }

    @PostMapping("/member/join")
    public String create(JoinForm joinForm) {
        Member member = new Member();
        member.setEmail(joinForm.getEmail());
        member.setName(joinForm.getName());
        member.setPassword(joinForm.getPassword());

        memberService.join(member);
        memberService.initVacationDays(member.getId());

        return "redirect:/";
    }

    @RequestMapping("/member/logout")
    public String logout(HttpServletRequest request) {
        HttpSession session = request.getSession();
        session.invalidate();
        return "redirect:/";
    }

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

//            return "vacationRequest";
            return "home";
        }
    }

    @GetMapping("/member/vacationRequest")
    public String createForm(HttpServletRequest request, Model model) {
        HttpSession session = request.getSession();
        long memberId = (long)session.getAttribute("memberId");
        int remainVacationDays = memberService.getRemainVacationDays(memberId);
        if (remainVacationDays <= 0) {
            model.addAttribute("msg", "남은 휴가가 없습니다.");
            return "home";
        }

        model.addAttribute("remainVacationDays", remainVacationDays);
//        model.addAttribute("member", member);

        return "vacationRequest";
    }

    @PostMapping("/member/createVacation")
    public String create(RequestVacationForm requestVacationForm, HttpServletRequest request) {

        HttpSession session = request.getSession();
        long memberId = (long)session.getAttribute("memberId");

        VacationRequest vacationRequest = new VacationRequest();
        vacationRequest.setMemberId(memberId);
        vacationRequest.setComments(requestVacationForm.getComment());

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date startDate = null;
        Date endDate = null;

        try {
            startDate = dateFormat.parse(requestVacationForm.getStartDate());
            endDate = dateFormat.parse(requestVacationForm.getEndDate());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        if (startDate != null) {
            vacationRequest.setStartAt(startDate);
        }

        if (endDate != null) {
            vacationRequest.setEndAt(endDate);
        }

        memberService.createVacation(vacationRequest);

        return "redirect:/member/vacationList";
    }

    @GetMapping("/member/vacationList")
    public String list(Long memberId, Model model) {
        List<VacationRequest> list = memberService.findVacationRequests(memberId);
        model.addAttribute("vacationList", list);
        return "vacationList";
    }

    @PostMapping("/member/cancelVacation")
    public String cancel(Long vacationId) {
        memberService.cancelVacation(vacationId);
        return "redirect:/member/vacationList";
    }
}
