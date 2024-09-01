package com.terry.demo.domain.member.sign.dto;

import lombok.*;

import java.util.List;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SigninDto {

    private Long memberId;
    private String idEmail;
    private String memberName;
    private List<String> memberAuthority;

}
