package com.terry.demo.domain.member.sign.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SigninResponse {

    private Long memberId;
    private String idEmail;
    private String memberName;
    private List<String> memberAuthority;

}
