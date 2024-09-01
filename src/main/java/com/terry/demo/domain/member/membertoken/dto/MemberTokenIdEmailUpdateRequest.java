package com.terry.demo.domain.member.membertoken.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberTokenIdEmailUpdateRequest {

    private String idEmail;
    private String reAccessToken;
    private String refreshToken;

}

