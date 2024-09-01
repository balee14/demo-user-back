package com.terry.demo.domain.member.member.dto.list;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberListRequest {


    private String  memberTypeState; //회원 사용여부

    private String memberJoinType; //회원 가입 구분


}

