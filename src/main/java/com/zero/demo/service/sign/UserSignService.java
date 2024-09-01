package com.zero.demo.service.sign;

import com.terry.demo.core.dto.CommonCustomResponse;
import com.terry.demo.core.dto.CommonCustomType;
import com.terry.demo.core.dto.CommonResponseEntity;
import com.terry.demo.core.dto.CommonResponseEntityType;
import com.terry.demo.core.entity.KpMemberToken;
import com.terry.demo.core.jwt.JwtTokenProvider;
import com.terry.demo.core.util.KpMemberUtil;
import com.terry.demo.membertoken.dto.MemberTokenIdEmailUpdateRequest;
import com.terry.demo.membertoken.service.MemberTokenService;
import com.terry.demo.sign.dto.SigninDto;
import com.terry.demo.sign.dto.SigninRequest;
import com.terry.demo.sign.dto.SigninResponse;
import com.terry.demo.sign.service.SignService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

@Log4j2
@Service
@Transactional
@RequiredArgsConstructor
public class UserSignService {

    private final SignService signService;
    private final MemberTokenService memberTokenService;
    private final JwtTokenProvider jwtTokenProvider;

    /**
     * 로그인
     * @param
     * @return
     */
    public ResponseEntity<?> getUserSignIn(SigninRequest signinRequest) {

        SigninResponse signinResponse = new SigninResponse();
        // HttpHeaders 설정
        HttpHeaders headers = new HttpHeaders();

        // 로그인
        SigninDto signinDto = signService.getMemberSignIn(signinRequest);
        signinResponse.setMemberId(signinDto.getMemberId());
        signinResponse.setIdEmail(signinDto.getIdEmail());
        signinResponse.setMemberName(signinDto.getMemberName());
        signinResponse.setMemberAuthority(signinDto.getMemberAuthority());

        // token 생성
        String accessToken = jwtTokenProvider.createAccessToken(signinDto);
        String refreshToken = jwtTokenProvider.createRefreshToken(signinDto);

        // token save
        KpMemberToken kpToken = new KpMemberToken();
        kpToken.setIdEmail(signinDto.getIdEmail());
        kpToken.setAccessToken(accessToken);
        kpToken.setRefreshToken(refreshToken);
        kpToken.setIsUse(true);
        memberTokenService.tokenSave(kpToken);

        headers.add("accessToken", accessToken);
        headers.add("refreshToken", refreshToken);

        return new ResponseEntity<>(new CommonResponseEntity<>(CommonResponseEntityType.OK, signinResponse)
            , headers, CommonResponseEntityType.OK.getHttpStatus());

    }

    /**
     * 토근 재발급
     * @param
     * @return
     */
    public ResponseEntity<?> getUserAccesstoken(HttpServletRequest request, HttpServletResponse response) {

        SigninResponse signinResponse = new SigninResponse();

        String idEmail = KpMemberUtil.getIdEmail();
        String refreshToken = jwtTokenProvider.getHeaderRefreshToken(request);

        // refreshToken이 일치 하는지 확인
        KpMemberToken kpMemberRefreshToken = memberTokenService.findByIdEmailAndRefreshToken(idEmail, refreshToken);
        if(ObjectUtils.isEmpty(kpMemberRefreshToken)) {
            throw new UsernameNotFoundException(CommonCustomType.CLIENT_ERROR_401_12.name());
        }

        // createAccessToken
        SigninDto signinDto = new SigninDto();
        signinDto.setIdEmail(idEmail);
        signinDto.setMemberAuthority(KpMemberUtil.getMemberAuthority());
        String reAccessToken = jwtTokenProvider.createAccessToken(signinDto);

        MemberTokenIdEmailUpdateRequest memberTokenIdEmailUpdateRequest = new MemberTokenIdEmailUpdateRequest();
        memberTokenIdEmailUpdateRequest.setIdEmail(idEmail);
        memberTokenIdEmailUpdateRequest.setRefreshToken(refreshToken);
        memberTokenIdEmailUpdateRequest.setReAccessToken(reAccessToken);
        memberTokenService.memberReAccessTokenUpdate(memberTokenIdEmailUpdateRequest);

        HttpHeaders headers = new HttpHeaders();
        headers.add("accessToken", reAccessToken);

        return new ResponseEntity<>(new CommonResponseEntity<>(CommonResponseEntityType.OK, signinResponse)
            , headers, CommonResponseEntityType.OK.getHttpStatus());

    }

    /**
     * 로그아웃
     * @param request
     * @param response
     * @return
     */
    public ResponseEntity<?> getUserSignLogout(HttpServletRequest request, HttpServletResponse response) {

        signService.getMemberSignOut(request, response);

        CommonCustomResponse commonCustomResponse = new CommonCustomResponse();
        commonCustomResponse.setCustomCode(CommonCustomType.getCustomCodeFindAny(CommonCustomType.SUCCESSFUL_200_1.name()));
        commonCustomResponse.setCustomMessage(CommonCustomType.getCustomMessageFindAny(CommonCustomType.SUCCESSFUL_200_1.name()));
        return new ResponseEntity<>(new CommonResponseEntity<>(CommonResponseEntityType.OK, commonCustomResponse)
            , CommonResponseEntityType.OK.getHttpStatus());
    }



}

