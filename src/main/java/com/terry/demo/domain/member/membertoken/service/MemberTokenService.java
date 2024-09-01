package com.terry.demo.domain.member.membertoken.service;

import com.terry.demo.core.entity.PfMemberToken;
import com.terry.demo.domain.member.membertoken.dto.MemberTokenErrorUpdate;
import com.terry.demo.domain.member.membertoken.dto.MemberTokenIdEmailUpdateRequest;
import com.terry.demo.domain.member.membertoken.repository.MemberTokenRepository;
import com.terry.demo.domain.member.membertoken.repository.MemberTokenRepositoryCustom;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberTokenService {

    private final MemberTokenRepository memberTokenRepository;
    private final MemberTokenRepositoryCustom memberTokenRepositoryCustom;

    /**
     * @param idEmail, accessToken
     * @return
     */
    public PfMemberToken findByIdEmailAndAccessTokenAndIsUse(String idEmail, String accessToken, boolean isUse) {
        return memberTokenRepository.findByIdEmailAndAccessTokenAndIsUse(idEmail, accessToken, isUse);
    }

    /**
     * @param idEmail, refreshToken
     * @return
     */
    public PfMemberToken findByIdEmailAndRefreshToken(String idEmail, String refreshToken) {
        return memberTokenRepository.findByIdEmailAndRefreshToken(idEmail, refreshToken);
    }

    /**
     * @param pfMemberToken
     */
    public void tokenSave(PfMemberToken pfMemberToken) {
        memberTokenRepository.save(pfMemberToken);
    }

    /**
     * @param membertokenIdEmailUpdateRequest
     */
    public void memberReAccessTokenUpdate(
        MemberTokenIdEmailUpdateRequest membertokenIdEmailUpdateRequest) {
        memberTokenRepositoryCustom.memberReAccessTokenUpdate(membertokenIdEmailUpdateRequest);
    }

    /**
     * 토큰 실패한 경우 error 처리
     * @param memberTokenErrorUpdate
     */
    public void memberTokenErrorUpdate(MemberTokenErrorUpdate memberTokenErrorUpdate) {
        memberTokenRepositoryCustom.memberTokenErrorUpdate(memberTokenErrorUpdate);
    }

    /**
     * refreshToken 또는 계정 전체 미사용 처리
     * jwt 오류가 발생 하면 accessToken이 처리 되기 때문에 refreshToken을 별도로 처리해 준다.
     * @param refreshToken
     */
    public void memberRefreshTokenErrorUpdate(String refreshToken, String idEmail) {

        // jwt 오류가 발생 하면 accessToken이 처리 되기 때문에 refreshToken을 별도로 처리해 준다.
        MemberTokenErrorUpdate memberTokenErrorUpdate = new MemberTokenErrorUpdate();
        memberTokenErrorUpdate.setRefreshToken(refreshToken);
        if (!ObjectUtils.isEmpty(idEmail)) {
            memberTokenErrorUpdate.setIdEmail(idEmail);
        }
        memberTokenErrorUpdate(memberTokenErrorUpdate);

    }

}

