package com.terry.demo.domain.member.core.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.terry.demo.core.dto.CommonCustomResponse;
import com.terry.demo.core.dto.CommonCustomType;
import com.terry.demo.core.dto.CommonResponseEntity;
import com.terry.demo.core.dto.CommonResponseEntityType;
import com.terry.demo.domain.member.core.jwt.JwtTokenProvider;
import com.terry.demo.domain.member.membertoken.dto.MemberTokenErrorUpdate;
import com.terry.demo.domain.member.membertoken.service.MemberTokenService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import java.io.IOException;

@Log4j2
@Component
@RequiredArgsConstructor
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private final JwtTokenProvider jwtTokenProvider;
    private final MemberTokenService memberTokenService;

//    private final HandlerExceptionResolver resolver;

//    public CustomAuthenticationEntryPoint(@Qualifier("handlerExceptionResolver") HandlerExceptionResolver resolver) {
//        this.resolver = resolver;
//    }

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authenticationException)
        throws IOException {

//        resolver.resolveException(request, response, null, authException);

        ResponseEntity<?> responseEntity;

        String accessToken = jwtTokenProvider.getHeaderAccessToken(request);
        if (ObjectUtils.isEmpty(accessToken)) {

            CommonCustomResponse commonCustomResponse = new CommonCustomResponse();
            commonCustomResponse.setCustomCode(CommonCustomType.getCustomCodeFindAny(CommonCustomType.CLIENT_ERROR_401_11.name()));
            commonCustomResponse.setCustomMessage(CommonCustomType.getCustomMessageFindAny(CommonCustomType.CLIENT_ERROR_401_11.name()));
            responseEntity = new ResponseEntity<>(new CommonResponseEntity<>(CommonResponseEntityType.UNAUTHORIZED, commonCustomResponse)
                , CommonResponseEntityType.UNAUTHORIZED.getHttpStatus());

        } else {

            // token 비활성화 처리
            MemberTokenErrorUpdate memberTokenErrorUpdate = new MemberTokenErrorUpdate();
            memberTokenErrorUpdate.setAccessToken(accessToken);
            memberTokenService.memberTokenErrorUpdate(memberTokenErrorUpdate);
            // Full authentication is required to access this resource
            responseEntity = new ResponseEntity<>(
                new CommonResponseEntity<>(CommonResponseEntityType.UNAUTHORIZED, authenticationException.toString())
                , CommonResponseEntityType.UNAUTHORIZED.getHttpStatus());

        }

        ObjectMapper objectMapper = new ObjectMapper();
        String jsonLogout = objectMapper.writeValueAsString(responseEntity.getBody());

        // application/json
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("utf-8");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.getWriter().write(jsonLogout);


    }



}
