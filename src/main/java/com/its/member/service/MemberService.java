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
        Long savedId = memberRepository.save(memberEntity).getId();

        return savedId;

    }

    public MemberDTO findById(Long id) {
        Optional<MemberEntity>optionalMemberEntity = memberRepository.findById(id);
        if(optionalMemberEntity.isPresent()){
            return toDTO(optionalMemberEntity.get());
        }else {
            return null;
        }
    }

    public MemberDTO login(MemberDTO memberDTO) {
        Optional<MemberEntity> optionalMemberEntity = memberRepository.findByMemberEmail(memberDTO.getMemberEmail());
        if (optionalMemberEntity.isPresent()){
            MemberEntity loginEntity = optionalMemberEntity.get();
            if (loginEntity.getMemberPassword().equals(memberDTO.getMemberPassword())){
                return MemberDTO.toDTO(loginEntity);
            }else {
                return null;
            }

        }else {
            return null;
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


    public void delete(Long id)  {
        memberRepository.deleteById(id);
    }

    public void update(MemberDTO memberDTO) {
        MemberEntity memberEntity = MemberEntity.toEntity(memberDTO);
        memberEntity.setId(memberDTO.getId());

        memberRepository.save(memberEntity);
    }

    public String dupCheck(String email) {
        Optional<MemberEntity> optionalMemberEntity = memberRepository.findByMemberEmail(email);
        if (optionalMemberEntity.isPresent()){
            return "no";
            }else{
            return "ok";
        }
    }
}
