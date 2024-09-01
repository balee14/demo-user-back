package com.terry.demo.domain.member.membertoken.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberTokenErrorUpdate {

    private String accessToken;
    private String refreshToken;
    private String idEmail;
    private String modId;

}

