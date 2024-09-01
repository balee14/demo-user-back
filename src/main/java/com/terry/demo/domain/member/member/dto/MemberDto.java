package com.terry.demo.domain.member.member.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberDto {

    private Long memberId;
    private String idEmail;
    private String name;

    @QueryProjection
    public MemberDto(Long memberId, String idEmail, String name) {
        this.memberId = memberId;
        this.idEmail = idEmail;
        this.name = name;
    }

}

