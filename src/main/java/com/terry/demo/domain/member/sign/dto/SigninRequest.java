package com.terry.demo.domain.member.sign.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SigninRequest {

    @NotNull
    @Size(min = 3, max = 50)
    private String idEmail;

    @NotNull
    @Size(min = 3, max = 100)
    private String pwd;
}
