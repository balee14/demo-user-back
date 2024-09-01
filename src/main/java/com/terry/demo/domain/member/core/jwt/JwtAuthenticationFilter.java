package com.terry.demo.domain.member.core.jwt;

import com.terry.demo.core.dto.CommonCustomType;
import com.terry.demo.domain.member.core.security.CustomAuthenticationException;
import com.terry.demo.domain.member.member.service.CustomUserDetailsService;
import com.terry.demo.domain.member.membertoken.service.MemberTokenService;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
@Log4j2
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtTokenProvider jwtTokenProvider;
    private final CustomUserDetailsService customUserDetailsService;
    private final MemberTokenService memberTokenService;


    /**
     *  정적 리소스 spring security 대상에서 제외
     * @return
     */
    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return web -> {
            web.ignoring()
                // 해당 경로는 security filter chain을 생략
                // 즉 permitAll로 설정하여 로그인 없이 접근 가능한 URL을 아래에 추가하여
                // 해당 URL 요청들은 JwtFilter, JwtExceptionFilter를 포함한 스프링 시큐리티의 필터 체인을 생략
                .requestMatchers("/api/user/v1/sign/in")
                .requestMatchers("/api/user/v1/member")
                //.requestMatchers(PathRequest.toStaticResources().atCommonLocations());
            ;
        };
    }

    /**
     * 토큰의 인증정보를 SecurityContext에 저장하는 역할 수행
     * @param request
     * @param response
     * @param filterChain
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

//        if (request.getMethod().equals("OPTIONS")) {
//            filterChain.doFilter(request, response);
//            return;
//        }

        String requestURI = request.getRequestURI();
        String accessToken = jwtTokenProvider.getHeaderAccessToken(request);
        String refreshToken = jwtTokenProvider.getHeaderRefreshToken(request);

        try {

            String idEmail;

            // 토큰 재발급은 실패 하였을 경우 해당 계정 토큰을 전체 미사용 처리 한다.
            // 안되면 refreshToken 미사용 처리
            if(requestURI.equals("/api/user/v1/sign/accesstoken")) {

                // refreshToken이 있는데 accessToekn이 없으면 refreshToken 또는 계정 전체 미사용 처리
                if (!ObjectUtils.isEmpty(refreshToken) && ObjectUtils.isEmpty(accessToken)) {
                    log.debug("jwt refresh error, uri: {}", requestURI);
                    throw new JwtException(CommonCustomType.CLIENT_ERROR_401_REFRESH_TOKEN_20.name());
                }

                // accessToekn이 있는데 refreshToken이 없는 경우 accessToekn 미사용 처리
                if (!ObjectUtils.isEmpty(accessToken) && ObjectUtils.isEmpty(refreshToken)) {
                    log.debug("jwt refresh error, uri: {}", requestURI);
                    throw new JwtException(CommonCustomType.CLIENT_ERROR_401_10.name());
                }

                // jwt refreshToken 확인 --> 실패 시 token error update
                if (!jwtTokenProvider.isValidateRefreshToken(refreshToken)) {
                    log.debug("jwt refresh error, uri: {}", requestURI);
                    throw new JwtException(CommonCustomType.CLIENT_ERROR_401_REFRESH_TOKEN_20.name());

                }

                // accessToekn check
                if (ObjectUtils.isEmpty(accessToken)) {
                    throw new CustomAuthenticationException(CommonCustomType.CLIENT_ERROR_401_11.name());
                }

                idEmail = jwtTokenProvider.getIdEmailRefreshToken(refreshToken);

                // 토큰 및 유저 확인 후 SetSecurityContextHolder
                customUserDetailsService.userDetailSetSecurityContextHolder(idEmail, accessToken, false);

            } else {

                // 로그인 url 패스 시키기 위해서 먼저 선언 되어야 함.(가장 상단에 선언해야함)
                if (ObjectUtils.isEmpty(accessToken)) {
                    // JWT null
                    throw new CustomAuthenticationException(CommonCustomType.CLIENT_ERROR_401_11.name());
                }

                // accessToken만 있어야 함. 그런데 refreshToken이 있다는 것은 토큰 탈취 가능성이 있기 때문에 해당 계정 토큰을 false(isUse)로 변경
                // refreshToken 토큰 먼저 처리 해야함. 안하면 "토큰 기한 만료"가 먼저 처리 되기 때문에 체크가 안됨.
                if (!ObjectUtils.isEmpty(refreshToken)) {
                    // jwt 오류가 발생 하면 accessToken이 처리 되기 때문에 refreshToken을 별도로 처리해 준다.
                    // refreshToken이 있으면 로직에 문제가 있기 때문에 refreshToken 또는 계정 전체 미사용 처리
                    memberTokenService.memberRefreshTokenErrorUpdate(refreshToken, null);
                    throw new JwtException(CommonCustomType.CLIENT_ERROR_401_REFRESH_TOKEN_20.name());
                }

                // jwt accessToken 확인
                if (!jwtTokenProvider.isValidateAccessToken(accessToken)) {
                    log.debug("JWT accessToken error, uri: {}", requestURI);
                    throw new JwtException(CommonCustomType.CLIENT_ERROR_401_10.name());
                    //throw new CustomAuthenticationException(CommonCustomType.CLIENT_ERROR_401_2.name());
                }

                // isValidateToken 함수 보다 앞에 있으면 "토큰 기한 만료"로 인한 재발급 토큰 로직을 실행 할 수 없음.
                idEmail = jwtTokenProvider.getIdEmailAccessToken(accessToken);

                // 토큰 및 유저 확인 후 SetSecurityContextHolder
                customUserDetailsService.userDetailSetSecurityContextHolder(idEmail, accessToken, true);

            }

        } catch (CustomAuthenticationException customAuthenticationException) {
            log.debug("customAuthenticationException--{}", customAuthenticationException.getClass());
        } catch (StringIndexOutOfBoundsException stringIndexOutOfBoundsException) {
            log.debug("stringIndexOutOfBoundsException--{}", stringIndexOutOfBoundsException.getClass());
        }

        filterChain.doFilter(request , response);

    }



}
