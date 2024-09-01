package com.terry.demo.domain.member.core.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.terry.demo.core.dto.CommonCustomResponse;
import com.terry.demo.core.dto.CommonCustomType;
import com.terry.demo.core.dto.CommonResponseEntity;
import com.terry.demo.core.dto.CommonResponseEntityType;
import com.terry.demo.domain.member.membertoken.dto.MemberTokenErrorUpdate;
import com.terry.demo.domain.member.membertoken.service.MemberTokenService;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;


@Log4j2
@Component
@RequiredArgsConstructor
public class JwtExceptionFilter extends OncePerRequestFilter {

    private final JwtTokenProvider jwtTokenProvider;
    private final MemberTokenService memberTokenService;

    /*
    인증 오류가 아닌, JWT 관련 오류는 이 필터에서 따로 잡아낸다.
    이를 통해 JWT 만료 에러와 인증 에러를 따로 잡아낼 수 있다.
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        try {
            chain.doFilter(request, response); // JwtAuthenticationFilter로 이동
        } catch (JwtException ex) {
            // JwtAuthenticationFilter에서 예외 발생하면 바로 setErrorResponse 호출
            setErrorResponse(request, response, ex);
        }
    }

    public void setErrorResponse(HttpServletRequest request, HttpServletResponse response, Throwable ex) throws IOException {

        ResponseEntity<?> responseEntity;

        String accessToken = jwtTokenProvider.getHeaderAccessToken(request);

        if(ex.toString().contains("JwtException")) {

            MemberTokenErrorUpdate memberTokenErrorUpdate = new MemberTokenErrorUpdate();
            // CLIENT_ERROR_401_9는 앞에서 idEmail로 토큰 전체 미사용 처리 하기 때문에 패스. 그외에는 accessToken 미사용 처리
            if (ex.getMessage().contains("CLIENT_ERROR_401_REFRESH_TOKEN_")) {

                String refreshToken = jwtTokenProvider.getHeaderRefreshToken(request);
                memberTokenService.memberRefreshTokenErrorUpdate(refreshToken, null);
                String idEmail = jwtTokenProvider.getIdEmailRefreshToken(refreshToken);
                memberTokenService.memberRefreshTokenErrorUpdate(refreshToken, idEmail);

            } else {

                memberTokenErrorUpdate.setAccessToken(accessToken);
                memberTokenService.memberTokenErrorUpdate(memberTokenErrorUpdate);

            }

            CommonCustomResponse commonCustomResponse = new CommonCustomResponse();
            commonCustomResponse.setCustomCode(CommonCustomType.getCustomCodeFindAny(ex.getMessage()));
            commonCustomResponse.setCustomMessage(CommonCustomType.getCustomMessageFindAny(ex.getMessage()));
            responseEntity = new ResponseEntity<>(
                new CommonResponseEntity<>(CommonResponseEntityType.UNAUTHORIZED, commonCustomResponse)
                , CommonResponseEntityType.UNAUTHORIZED.getHttpStatus());
        } else {

            responseEntity = new ResponseEntity<>(
                new CommonResponseEntity<>(CommonResponseEntityType.UNAUTHORIZED, ex.toString())
                , CommonResponseEntityType.UNAUTHORIZED.getHttpStatus());

        }

        ObjectMapper objectMapper = new ObjectMapper();
        String jsonLogout = objectMapper.writeValueAsString(responseEntity.getBody());

        // application/json;charset=UTF-8
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("utf-8");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.getWriter().write(jsonLogout);

    }



}
