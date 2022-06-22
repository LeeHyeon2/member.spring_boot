package com.its.member.repository;

import com.its.member.dto.MemberDTO;
import com.its.member.entity.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface MemberRepository extends JpaRepository<MemberEntity,Long> {

    @Query("select m from MemberEntity m where m.memberEmail = :email and m.memberPassword = :password")
    MemberEntity login(String email, String password);
}
