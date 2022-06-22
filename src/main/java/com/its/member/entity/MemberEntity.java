package com.its.member.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter@Setter
@Table(name = "member_table")
public class MemberEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "memberEmail",length = 50)
    private String memberEmail;
    @Column(name = "memberPassword",length = 20)
    private String memberPassword;
    @Column(name = "memberName",length = 20)
    private String memberName;
    @Column(name = "memberAge")
    private Long memberAge;
    @Column(name = "memberPhone",length = 30)
    private String memberPhone;


}
