package com.terry.demo.domain.member.member.dto.list;


import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class MemberListResponse {

    private List<MembersDto> memberList;
    private Long allCount;

}

