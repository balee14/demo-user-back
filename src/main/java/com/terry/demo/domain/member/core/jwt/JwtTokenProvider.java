package com.terry.demo.domain.member.core.jwt;

import com.terry.demo.core.dto.CommonCustomType;
import com.terry.demo.core.util.PfCommonUtils;
import com.terry.demo.domain.member.sign.dto.SigninDto;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.security.Key;
import java.time.Clock;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

@Log4j2
@Component
public class JwtTokenProvider implements InitializingBean {

    private static final String AUTHORITIES_KEY = "auth";
    private final String secretKeyStr;
    private final long expirationMinutes;
    private final long refreshExpirationHours;
    private final String issuer;
    private Key secretKey;

    public JwtTokenProvider(@Value("${jwt.secret-key}") String secretKeyStr
        , @Value("${jwt.expiration-minutes}") long expirationMinutes
        , @Value("${jwt.refresh-expiration-hours}") long refreshExpirationHours
        , @Value("${jwt.issuer}") String issuer) {
        this.secretKeyStr = secretKeyStr;
        this.expirationMinutes = expirationMinutes;
        this.refreshExpirationHours = refreshExpirationHours;
        this.issuer = issuer;
    }

    /**
     * 빈이 생성되고 주입을 받은 후에 secret값을 Base64 Decode해서 key 변수에 할당하기 위해
     */
    @Override
    public void afterPropertiesSet() {
        byte[] secretKeyBytes = Decoders.BASE64.decode(secretKeyStr);
        this.secretKey = Keys.hmacShaKeyFor(secretKeyBytes);
    }

    /**
     * AccessToken생성
     * @return
     */
    public String createAccessToken(SigninDto signinDto) {

        String memberAuthorityStr = PfCommonUtils.listStringToString(signinDto.getMemberAuthority(), ",");

        return Jwts.builder()
            .subject(signinDto.getIdEmail())   // 사용자 식별자값(ID)
            .claim(AUTHORITIES_KEY, memberAuthorityStr) // 사용자 권한
            .signWith(secretKey) // 사용할 암호화 알고리즘과 , signature 에 들어갈 secret값 세팅
            .expiration(Date.from(Instant.now().plus(expirationMinutes, ChronoUnit.MINUTES)))    // set Expire Time 해당 옵션 안넣으면 expire안함
//            .expiration(Date.from(Instant.now().plus(expirationMinutes, ChronoUnit.SECONDS)))
            .compact();

    }


    /**
     * RefreshToken생성
     * @return
     */
    public String createRefreshToken(SigninDto signinDto) {
        return Jwts.builder()
            .subject(signinDto.getIdEmail())   // 사용자 식별자값(ID)
            .issuer(issuer)
            .issuedAt(Date.from(Instant.now(Clock.systemDefaultZone())))
            .signWith(secretKey)
            .expiration(Date.from(Instant.now().plus(refreshExpirationHours, ChronoUnit.HOURS)))    // set Expire Time 해당 옵션 안넣으면 expire안함
//            .expiration(Date.from(Instant.now().plus(refreshExpirationHours, ChronoUnit.SECONDS)))
            .compact();
    }

    /**
     * 토큰의 유효성 검증을 수행
     * @param token
     * @return
     * @throws ExpiredJwtException
     */
    public boolean isValidateAccessToken(String token) {

        try {
            Jwts.parser().setSigningKey(secretKey).build().parseClaimsJws(token);
            return true;
        } catch (SecurityException e) {
            log.info("jwt 잘못된 시그니처");
            throw new JwtException(CommonCustomType.CLIENT_ERROR_401_13.name());
        } catch (MalformedJwtException e) {
            log.info("jwt 유효하지 않은 토큰");
            throw new JwtException(CommonCustomType.CLIENT_ERROR_401_14.name());
        } catch (ExpiredJwtException e) {
            log.info("jwt 토큰 기한 만료");
            throw new JwtException(CommonCustomType.CLIENT_ERROR_401_15.name());
        } catch (UnsupportedJwtException e) {
            log.info("jwt 지원되지 않는 토큰");
            throw new JwtException(CommonCustomType.CLIENT_ERROR_401_16.name());
        } catch (IllegalArgumentException e) {
            log.info("jwt token compact of handler are invalid");
            throw new JwtException(CommonCustomType.CLIENT_ERROR_401_17.name());
        } catch (JwtException e) {
            log.info("jwt error");
        }
        return false;

    }

    /**
     * 토큰의 유효성 검증을 수행
     * @param refreshToken
     * @return
     * @throws ExpiredJwtException
     */
    public boolean isValidateRefreshToken(String refreshToken) {

        try {
            Jwts.parser().setSigningKey(secretKey).build().parseClaimsJws(refreshToken);
            return true;
        } catch (SecurityException e) {
            log.info("refresh 잘못된 JWT 시그니처");
        } catch (MalformedJwtException e) {
            log.info("refresh 유효하지 않은 JWT 토큰");
        } catch (ExpiredJwtException e) {
            log.info("refresh 토큰 기한 만료");
            throw new JwtException(CommonCustomType.CLIENT_ERROR_401_REFRESH_TOKEN_25.name());
        } catch (UnsupportedJwtException e) {
            log.info("refresh 지원되지 않는 JWT 토큰입니다.");
        } catch (IllegalArgumentException e) {
            log.info("refresh JWT token compact of handler are invalid.");
        }
        return false;

    }

    /**
     *
     * @param token
     * @return
     */
    public String getIdEmailAccessToken(String token) throws RuntimeException{
        try {

            Claims claims = Jwts
                .parser()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody();
            return claims.getSubject();

        } catch (Exception e) {
            log.debug("jwt accessToken getIdEmail null");
            throw new JwtException(CommonCustomType.CLIENT_ERROR_401_18.name());
        }
    }

    /**
     *
     * @param token
     * @return
     */
    public String getIdEmailRefreshToken(String token) throws RuntimeException{
        try {

            Claims claims = Jwts
                .parser()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody();
            return claims.getSubject();

        } catch (Exception e) {
            log.debug("jwt accessToken getIdEmail null");
            //throw new JwtException(CommonCustomType.CLIENT_ERROR_401_REFRESH_TOKEN_23.name());
        }
        return null;
    }

    /**
     * 토큰으로 클레임을 만들고 이를 이용해 유저 객체를 만들어서 최종적으로 authentication 객체를 리턴
     * @param token
     * @return
     */
    public Authentication getAuthentication(String token) {

        Claims claims = Jwts
                .parser()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody();

        Collection<? extends GrantedAuthority> authorities =
                Arrays.stream(claims.get(AUTHORITIES_KEY).toString().split(","))
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList());

        User principal = new User(claims.getSubject(), "", authorities);
        return new UsernamePasswordAuthenticationToken(principal, token, authorities);

    }

    /**
     * Request Header 에서 토큰 정보를 꺼내오기 위한 메소드
     * @param request
     * @return
     */
    public String getHeaderAccessToken(HttpServletRequest request) {

        String bearerToken = request.getHeader("Authorization");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;

    }

    /**
     * Request의 Header에서 RefreshToken 값을 가져옵니다. "Authorization" : "token'
     * @param request
     * @return
     */
    public String getHeaderRefreshToken(HttpServletRequest request) {

        if(request.getHeader("refreshToken") != null )
            return request.getHeader("refreshToken");
        return null;

    }



}
