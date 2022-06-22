package com.its.member.Test;

import com.its.member.dto.MemberDTO;
import com.its.member.service.MemberService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import java.util.stream.IntStream;


import static org.assertj.core.api.Assertions.*;
@SpringBootTest
public class TestClass {
    @Autowired
    private MemberService memberService;

    @Test
    @Transactional
    @Rollback(value = true)
    @DisplayName("save 테스트")
    public void saveTest(){
        MemberDTO memberDTO = new MemberDTO("Test","Test","Test",1L,"Test");
        Long saveId = memberService.save(memberDTO);
        MemberDTO findDTO = memberService.findById(saveId);

        assertThat(memberDTO.getMemberEmail()).isEqualTo(findDTO.getMemberEmail());
    }

    @Test
    @Transactional
    @Rollback(value = true)
    @DisplayName("login 테스트")
    public void loginTest(){
        MemberDTO memberDTO = new MemberDTO("Test","Test","Test",1L,"Test");
        memberService.save(memberDTO);

        MemberDTO loginDTO = new MemberDTO("Test","Test","Test",1L,"Test");
        boolean login = memberService.login(loginDTO);

        assertThat(login).isEqualTo(true);
    }
}
