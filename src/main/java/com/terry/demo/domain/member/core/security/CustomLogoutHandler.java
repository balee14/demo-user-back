package com.terry.demo.domain.member.core.security;

import com.terry.demo.domain.member.core.jwt.JwtTokenProvider;
import com.terry.demo.domain.member.member.service.CustomUserDetailsService;
import com.terry.demo.domain.member.sign.service.SignService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Component;

@Log4j2
@Component
@RequiredArgsConstructor
public class CustomLogoutHandler implements LogoutHandler {

    private final JwtTokenProvider jwtTokenProvider;
    private final CustomUserDetailsService customUserDetailsService;
    private final SignService signService;

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response,
                       Authentication authentication) {

        String accessToken = jwtTokenProvider.getHeaderAccessToken(request);
        String idEmail = jwtTokenProvider.getIdEmailAccessToken(accessToken);

        // 유저 확인
        customUserDetailsService.userDetailSetSecurityContextHolder(idEmail, accessToken, true);

        // 로그아웃
        signService.getMemberSignOut(request, response);

    }

}
