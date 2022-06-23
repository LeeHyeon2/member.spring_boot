package com.its.member.Test;

import com.its.member.dto.MemberDTO;
import com.its.member.service.MemberService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.IntStream;


import static org.assertj.core.api.Assertions.*;
@SpringBootTest
public class MemberTest {
    @Autowired
    private MemberService memberService;

    public MemberDTO newMember(int i) {
        MemberDTO member = new MemberDTO("테스트이메일"+i, "테스트비밀번호"+i, "테스트이름"+i, 99l+i, "테스트전화번호"+i);
        return member;
    }
    @Test
    @Transactional
    @Rollback(value = true)
    @DisplayName("save 테스트")
    public void saveTest(){
        Long saveId = memberService.save(newMember(1));
        MemberDTO findDTO = memberService.findById(saveId);

        assertThat(newMember(1).getMemberEmail()).isEqualTo(findDTO.getMemberEmail());
    }

    @Test
    @Transactional
    @Rollback(value = true)
    @DisplayName("login 테스트")
    public void loginTest(){
        MemberDTO memberDTO = new MemberDTO("Test","Test","Test",1L,"Test");
        memberService.save(memberDTO);

        MemberDTO loginDTO = new MemberDTO("Test","Test","Test",1L,"Test");
        MemberDTO login = memberService.login(loginDTO);

        assertThat(login).isNotNull();
    }

    @Test
    @Transactional
    @Rollback(value = true)
    @DisplayName("findAll 테스트")
    public void findAllTest(){
        IntStream.rangeClosed(1, 3).forEach(i -> {
            MemberDTO memberDTO = new MemberDTO("Test" + i,"Test" + i,"Test" + i, (long) i,"Test" + i);
            memberService.save(memberDTO);
        });

        List<MemberDTO> memberDTOList = memberService.findAll();

        assertThat(memberDTOList.size()).isEqualTo(3);
    }

    @Test
    @Transactional
    @Rollback(value = true)
    @DisplayName("update 테스트")
    public void deleteTest(){
        Long saveId = memberService.save(newMember(1));
        memberService.delete(saveId);
        assertThat(memberService.findById(saveId)).isNull();
    }

    @Test
    @Transactional
    @Rollback(value = true)
    @DisplayName("update 테스트")
    public void updateTest(){
        // 데이터만들기
        // 첫 데이터 만들기
        Long saveId = memberService.save(newMember(1));
        // 첫 데이터 저장
        MemberDTO saveDTO = memberService.findById(saveId);
        // 수정 데이터
        MemberDTO memberDTO = new MemberDTO(saveId,"Test","Test","Test",1L,"Test");
        // 수정처리
        memberService.update(memberDTO);
        // 수정 데이터 저장
        MemberDTO updateDTO = memberService.findById(saveId);
        // test
        assertThat(saveDTO.getMemberEmail()).isNotEqualTo(updateDTO.getMemberEmail());

    }

    @Test
    @DisplayName("회원 데이터 저장")
    public void memberSave(){
        IntStream.rangeClosed(1,20).forEach(i -> {
            memberService.save(newMember(i));

        });
    }
}
