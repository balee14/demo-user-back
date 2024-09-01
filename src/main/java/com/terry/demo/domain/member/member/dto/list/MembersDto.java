package com.terry.demo.domain.member.member.dto.list;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MembersDto {

    private Long memberId;
    private String idEmail;
    private String name;

    @QueryProjection
    public MembersDto(Long memberId, String idEmail, String name) {
        this.memberId = memberId;
        this.idEmail = idEmail;
        this.name = name;
    }

}

