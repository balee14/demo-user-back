package com.terry.demo.domain.member.core.security;

import lombok.Getter;
import org.springframework.security.core.AuthenticationException;


@Getter
public class CustomAuthenticationException extends AuthenticationException {

    public CustomAuthenticationException(String message) {
        super(message);
    }



}

