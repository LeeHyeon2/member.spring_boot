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
        memberService.save(memberDTO);

        return "redirect:/member/login-form";
    }

    @GetMapping("/login-form")
    public String loginForm(){
        return "/memberPages/login";
    }

    @PostMapping("/login")
    public String login(@ModelAttribute MemberDTO memberDTO ,  HttpSession session) {
        /**
         * login.html 에서 이메일 , 비번을 받아오고
         * DB로 부터 해당 이메일의 정보를 가져와서
         * 입력받은 비번과 db예서 조회한 비번의 일치여부를 판단하여
         * 일치하면 로그인 성공, 일치하지 않으면 로그인 실패로 처리
         */

        MemberDTO login = memberService.login(memberDTO);

        if(login != null){
            session.setAttribute("loginEmail",login.getMemberEmail());
            session.setAttribute("id",login.getId());
            return "/memberPages/main";
        }else {
            return "/index";
        }
    }

    @GetMapping("/")
    public String main(Model model){
        List<MemberDTO> memberDTOList = memberService.findAll();
        model.addAttribute("memberDTOList",memberDTOList);
        return "/memberPages/list";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id){
        memberService.delete(id);
        return "redirect:/member/";
    }

    @GetMapping("/update/{id}")
    public String updateForm(@PathVariable Long id , Model model){
        MemberDTO memberDTO = memberService.findById(id);
        model.addAttribute("memberDTO",memberDTO);
        return "/memberPages/update";
    }

    @PostMapping("/update")
    public String update(@ModelAttribute MemberDTO memberDTO){
        memberService.update(memberDTO);
        return "/memberPages/main";
    }

    // /member/3
    // /member?id=3
    @GetMapping("/{id}")
    public String findById(@PathVariable Long id , Model model){
        MemberDTO memberDTO = memberService.findById(id);
        model.addAttribute("memberDTO",memberDTO);
        return "/memberPages/detail";
    }

    // ajax 상세조회
    @GetMapping("/ajax/{id}")
    public @ResponseBody MemberDTO ajaxId(@PathVariable Long id){
        return memberService.findById(id);
    }
}
