package com.its.member.controller;

import com.its.member.dto.MemberDTO;
import com.its.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {
    private final MemberService memberService;

    @GetMapping("/save-form")
    public String saveForm(){
        return "/memberPages/save";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute MemberDTO memberDTO){
        Long id = memberService.save(memberDTO);

        if (id >= 0){
            return "redirect:/member/login-form";
        }else {
            return "/index";
        }
    }

    @GetMapping("/login-form")
    public String loginForm(){
        return "/memberPages/login";
    }

    @PostMapping("/login")
    public String login(@ModelAttribute MemberDTO memberDTO , HttpServletRequest request) {
        HttpSession session = request.getSession();
        Boolean login = memberService.login(memberDTO);

        if(login){
            session.setAttribute("loginEmail",memberDTO.getMemberEmail());
            return "/memberPages/main";
        }else {
            return "/index";
        }
    }

    @GetMapping("/")
    public String main(Model model){
        List<MemberDTO> memberDTOList = memberService.findAll();
        model.addAttribute("memberDTOList",memberDTOList);
        return "/memberPages/member";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id){
        memberService.delete(id);
        return "redirect:/member/";
    }


}
