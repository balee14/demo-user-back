package com.terry.demo.core.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

@Getter
@RequiredArgsConstructor
public enum CommonCustomType {

    // 200 번대
    SUCCESSFUL_200_1(CommonResponseEntityType.OK.getResponseCode(), "1","로그아웃"),
    SUCCESSFUL_200_2(CommonResponseEntityType.OK.getResponseCode(), "2","이미 가입되어 있는 유저입니다."),
    // 패스워드 불일치
    SUCCESSFUL_200_5(CommonResponseEntityType.OK.getResponseCode(), "5","자격 증명에 실패하였습니다."),
    // 400 번대
    CLIENT_ERROR_400_1(CommonResponseEntityType.BAD_REQUEST.getResponseCode(),"1","kioskId가 없습니다."),
    // 401 번대
    // 1번대 JWT 관련
    CLIENT_ERROR_401_1(CommonResponseEntityType.UNAUTHORIZED.getResponseCode(),"1","JWT error"),
    CLIENT_ERROR_401_2(CommonResponseEntityType.UNAUTHORIZED.getResponseCode(),"2","유효하지 않은 JWT 토큰"),
    // 10번대 accessToken
    CLIENT_ERROR_401_10(CommonResponseEntityType.UNAUTHORIZED.getResponseCode(),"10","jwt accessToken error"),
    CLIENT_ERROR_401_11(CommonResponseEntityType.UNAUTHORIZED.getResponseCode(),"11","jwt accessToken null"),
    CLIENT_ERROR_401_12(CommonResponseEntityType.UNAUTHORIZED.getResponseCode(),"12","jwt accessToken db null"),
    CLIENT_ERROR_401_13(CommonResponseEntityType.UNAUTHORIZED.getResponseCode(),"13","jwt 잘못된 시그니처"),
    CLIENT_ERROR_401_14(CommonResponseEntityType.UNAUTHORIZED.getResponseCode(),"14","jwt 유효하지 않은 토큰"),
    CLIENT_ERROR_401_15(CommonResponseEntityType.UNAUTHORIZED.getResponseCode(),"15","jwt 토큰 기한 만료"),
    CLIENT_ERROR_401_16(CommonResponseEntityType.UNAUTHORIZED.getResponseCode(),"16","jwt 지원되지 않는 토큰"),
    CLIENT_ERROR_401_17(CommonResponseEntityType.UNAUTHORIZED.getResponseCode(),"17","jwt token compact of handler are invalid"),
    CLIENT_ERROR_401_18(CommonResponseEntityType.UNAUTHORIZED.getResponseCode(),"18","jwt accessToken getIdEmail null"),
    // 20번대 refreshToken
    CLIENT_ERROR_401_REFRESH_TOKEN_20(CommonResponseEntityType.UNAUTHORIZED.getResponseCode(),"20","jwt refreshToken error"),
    CLIENT_ERROR_401_REFRESH_TOKEN_21(CommonResponseEntityType.UNAUTHORIZED.getResponseCode(),"21","jwt refreshToken null"),
    CLIENT_ERROR_401_REFRESH_TOKEN_22(CommonResponseEntityType.UNAUTHORIZED.getResponseCode(),"22","jwt refreshToken db null"),
    CLIENT_ERROR_401_REFRESH_TOKEN_23(CommonResponseEntityType.UNAUTHORIZED.getResponseCode(),"22","jwt refreshToken getIdEmail null"),
    CLIENT_ERROR_401_REFRESH_TOKEN_25(CommonResponseEntityType.UNAUTHORIZED.getResponseCode(),"25","jwt refreshToken 토큰 기한 만료"),
    // 30번대 사용자
    CLIENT_ERROR_401_30(CommonResponseEntityType.UNAUTHORIZED.getResponseCode(),"30","유저 정보 불일치"),
    CLIENT_ERROR_401_31(CommonResponseEntityType.UNAUTHORIZED.getResponseCode(),"31","PfUserUtil.getUserId"),
    CLIENT_ERROR_401_32(CommonResponseEntityType.UNAUTHORIZED.getResponseCode(),"32","PfUserUtil.getIdEmail"),
    CLIENT_ERROR_401_33(CommonResponseEntityType.UNAUTHORIZED.getResponseCode(),"33","PfUserUtil.getUserName"),

    // 403 번대 --> ADMIN, PARTNER 관련 권한 처리 코드
    CLIENT_ERROR_403_1(CommonResponseEntityType.FORBIDDEN.getResponseCode(),"1",""),
    // 500번대
    SERVER_ERROR_500_1(CommonResponseEntityType.INTERNAL_SERVER_ERROR.getResponseCode(),"1","")
    ;

    private final int responseCode;
    private final String customCode;
    private final String customMessage;

    public static int getResponseCodeFindAny(String name) {

        return Arrays.stream(CommonCustomType.values())
            .filter(v -> v.name().equalsIgnoreCase(name))
            .findAny().get().getResponseCode();
    }

    public static String getCustomCodeFindAny(String name) {

        return Arrays.stream(CommonCustomType.values())
            .filter(v -> v.name().equalsIgnoreCase(name))
            .findAny().get().getCustomCode();
    }

    public static String getCustomMessageFindAny(String name) {

        return Arrays.stream(CommonCustomType.values())
            .filter(v -> v.name().equalsIgnoreCase(name))
            .findAny().get().getCustomMessage();
    }

}
