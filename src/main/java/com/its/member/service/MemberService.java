package com.its.member.service;

import com.its.member.dto.MemberDTO;
import com.its.member.entity.MemberEntity;
import com.its.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.its.member.dto.MemberDTO.toDTO;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;

    public Long save(MemberDTO memberDTO) {
        MemberEntity memberEntity = MemberEntity.toEntity(memberDTO);
        Long id = memberRepository.save(memberEntity).getId();

        return id;

    }

    public MemberDTO findById(Long id) {
        Optional<MemberEntity>optionalMemberEntity = memberRepository.findById(id);
        if(optionalMemberEntity.isPresent()){
            return toDTO(optionalMemberEntity.get());
        }else {
            return null;
        }
    }

    public Boolean login(MemberDTO memberDTO) {
        MemberEntity memberEntity = MemberEntity.toEntity(memberDTO);
        MemberEntity memberEntity1 = this.memberRepository.login(memberEntity.getMemberEmail(),memberEntity.getMemberPassword());

        if (memberEntity1 == null){
            return false;
        } else {
            return true;
        }
    }

    public List<MemberDTO> findAll() {
        List<MemberEntity> memberEntities = memberRepository.findAll();
        List<MemberDTO> memberDTOList = new ArrayList<>();
        for(MemberEntity memberEntity : memberEntities){
            MemberDTO memberDTO = toDTO(memberEntity);
            memberDTOList.add(memberDTO);
        }
        return memberDTOList;
    }


    public void delete(Long id) {
        memberRepository.deleteById(id);
    }
}
