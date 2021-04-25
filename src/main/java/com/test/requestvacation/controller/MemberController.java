package com.test.requestvacation.controller;

import com.test.requestvacation.entity.Member;
import com.test.requestvacation.entity.MemberVacation;
import com.test.requestvacation.service.MemberService;
import com.test.requestvacation.service.MemberVacationService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class MemberController {

    private MemberService memberService;
    private MemberVacationService memberVacationService;

    public MemberController(MemberService memberService, MemberVacationService memberVacationService) {
        this.memberService = memberService;
        this.memberVacationService = memberVacationService;
    }

    @GetMapping("/member/join")
    public String createJoinForm() {
        return "join";
    }

    @PostMapping("/member/join")
    public String create(@RequestParam String name, @RequestParam String email, @RequestParam String password) {
        Member member = new Member();
        member.setEmail(email);
        member.setName(name);
        member.setPassword(password);

        try {
            memberService.join(member);
            memberVacationService.initVacationDays(member.getId());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return "redirect:/";
    }

    @PostMapping("/member/login")
    public String login(String email, String password, Model model) {
        Member member = memberService.login(email, password);
        if (member == null) {
            model.addAttribute("msg", "로그인에 실패했습니다. 로그인 정보를 확인해주세요.");
            return "home";
        } else {
            int remainVacationDays = memberVacationService.getRemainVacationDays(member.getId());
            if (remainVacationDays <= 0) {
                model.addAttribute("msg", "남은 휴가가 없습니다.");
                return "home";
            }
            model.addAttribute("remainVacationDays", remainVacationDays);
            model.addAttribute("member", member);
            return "requestVacation";
        }
    }
}
