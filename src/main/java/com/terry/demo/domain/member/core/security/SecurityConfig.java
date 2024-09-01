package com.terry.demo.domain.member.core.security;


import com.terry.demo.core.enums.EnvTypeEnum;
import com.terry.demo.core.util.PfPropertyUtil;
import com.terry.demo.domain.member.core.jwt.JwtAuthenticationFilter;
import com.terry.demo.domain.member.core.jwt.JwtExceptionFilter;
import com.terry.demo.domain.member.core.jwt.JwtTokenProvider;
import com.terry.demo.domain.member.member.service.CustomUserDetailsService;
import com.terry.demo.domain.member.membertoken.service.MemberTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtTokenProvider jwtTokenProvider;
    private final JwtExceptionFilter jwtExceptionFilter;
    private final CustomAuthenticationEntryPoint customAuthenticationEntryPoint;
    private final CustomAccessDeniedHandler customAccessDeniedHandler;
    private final CustomLogoutHandler customLogoutHandler;
    private final CustomLogoutSuccessHandler customLogoutSuccessHandler;
    private final CustomUserDetailsService customUserDetailsService;
    private final MemberTokenService memberTokenService;

//    public static final String ALLOWED_IP_ADDRESS = "111.111.111.111";
//    public static final String SUBNET = "/32";
//    public static final IpAddressMatcher ALLOWED_IP_ADDRESS_MATCHER = new IpAddressMatcher(ALLOWED_IP_ADDRESS + SUBNET);
//    public static final String IP_CHECK_PATH_PREFIX = "/api/temp";
//    public static final String IP_CHECK_PATH_PATTERN = IP_CHECK_PATH_PREFIX + "/**";

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("http://localhost:3013"));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(List.of("*"));
        configuration.setExposedHeaders(Collections.singletonList("*"));
        //configuration.setAllowedHeaders(Arrays.asList("X-Requested-With", "Content-Type", "Authorization", "X-XSRF-token"));
        configuration.setAllowCredentials(true);   // 쿠키를 받을건지
        configuration.setMaxAge(3600L);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {

        httpSecurity
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .csrf(AbstractHttpConfigurer::disable)  // CSRF 설정 비활성화(세션 방식일 때는 필요)
                .httpBasic(AbstractHttpConfigurer::disable)
                .formLogin(AbstractHttpConfigurer::disable)
                .sessionManagement(
                    // 기본 세션 방식 대신 JWT 방식 사용 설정
                    (sessionManagement) -> sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                );

        //  local여부 확인 : local이면 개발 편의성을 인증 확인 안하고 패스 시킨다.
        if(EnvTypeEnum.isJwtType(PfPropertyUtil.getProperty("spring.profiles.active"))) {

            httpSecurity.authorizeHttpRequests((authorize) -> authorize
                .requestMatchers("/api/user/v1/sign/in").permitAll() // 로그인
                //.requestMatchers(HttpMethod.POST, "/api/admin/v1/member").permitAll() // 회원가입
                //.requestMatchers(HttpMethod.GET, "/api/admin/v1/sign/logout").permitAll() // 로그아웃
                //.requestMatchers(IP_CHECK_PATH_PATTERN).access(this::hasIpAddress)
                .anyRequest().authenticated()
            );

        } else {

            httpSecurity.authorizeHttpRequests((authorize) -> authorize
                    .requestMatchers("/**").permitAll() // 전체
            );

        }
        httpSecurity
                // 로그아웃 설정
                .logout(logout -> logout
                        .logoutUrl("/api/user/v1/sign/out")
                        .addLogoutHandler(customLogoutHandler)
                        .logoutSuccessHandler(customLogoutSuccessHandler)
                );
        httpSecurity.addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider, customUserDetailsService, memberTokenService), UsernamePasswordAuthenticationFilter.class)
        .addFilterBefore(jwtExceptionFilter, JwtAuthenticationFilter.class);
        httpSecurity.exceptionHandling(handler -> handler
            .authenticationEntryPoint(customAuthenticationEntryPoint)
            .accessDeniedHandler(customAccessDeniedHandler)
        );
        return httpSecurity.build();

    }

    /**
     * 허용되는 IP설정
     * @param authentication
     * @param object
     * @return
     */
    /*
    private AuthorizationDecision hasIpAddress(Supplier<Authentication> authentication, RequestAuthorizationContext object) {
        return new AuthorizationDecision(
                !(authentication.get() instanceof AnonymousAuthenticationToken)
                        && ALLOWED_IP_ADDRESS_MATCHER.matches(object.getRequest()
                ));
    }
    */


}
