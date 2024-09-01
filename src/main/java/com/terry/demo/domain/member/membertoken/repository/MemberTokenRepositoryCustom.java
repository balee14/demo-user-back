package com.terry.demo.domain.member.membertoken.repository;

import com.terry.demo.domain.member.membertoken.dto.MemberTokenErrorUpdate;
import com.terry.demo.domain.member.membertoken.dto.MemberTokenIdEmailUpdateRequest;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.querydsl.jpa.impl.JPAUpdateClause;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.util.ObjectUtils;

import java.time.LocalDateTime;

import static com.terry.demo.core.entity.QPfMemberToken.pfMemberToken;

@Repository
@RequiredArgsConstructor
public class MemberTokenRepositoryCustom {

    private final JPAQueryFactory jPAQueryFactory;

/**
     * 토큰 관련 업데이트
     *
     * @param memberTokenIdEmailUpdateRequest
     */
    public void memberReAccessTokenUpdate(
        MemberTokenIdEmailUpdateRequest memberTokenIdEmailUpdateRequest) {

        JPAUpdateClause clause = jPAQueryFactory
            .update(pfMemberToken)
            .set(pfMemberToken.isUse, true)
            .set(pfMemberToken.accessToken, memberTokenIdEmailUpdateRequest.getReAccessToken())
            .set(pfMemberToken.accessTokenDt, LocalDateTime.now())
            .set(pfMemberToken.modDt, LocalDateTime.now())
            .set(pfMemberToken.modId, memberTokenIdEmailUpdateRequest.getIdEmail())
            .where(pfMemberToken.refreshToken.eq(memberTokenIdEmailUpdateRequest.getRefreshToken())
                    , pfMemberToken.idEmail.eq(memberTokenIdEmailUpdateRequest.getIdEmail())
                    //, pfMemberToken.isUse.eq()
            );
        clause.execute();

    }

    /**
     * 토큰 관련 업데이트
     *
     * @param memberTokenErrorUpdate
     */
    public void memberTokenErrorUpdate(MemberTokenErrorUpdate memberTokenErrorUpdate) {

        JPAUpdateClause clause = jPAQueryFactory
                .update(pfMemberToken)
                .set(pfMemberToken.modDt, LocalDateTime.now())
                .set(pfMemberToken.isUse, false);
                if (!ObjectUtils.isEmpty(memberTokenErrorUpdate.getIdEmail())) {
                    // 상위 개념인 idEmail이 먼저 처리 해야 함.
                    clause.set(pfMemberToken.modId, memberTokenErrorUpdate.getIdEmail());
                    clause.where(pfMemberToken.idEmail.eq(memberTokenErrorUpdate.getIdEmail())
                        , pfMemberToken.isUse.eq(true)
                    );
                } else if (!ObjectUtils.isEmpty(memberTokenErrorUpdate.getRefreshToken())){
                    clause.set(pfMemberToken.modId, memberTokenErrorUpdate.getRefreshToken());
                    clause.where(pfMemberToken.refreshToken.eq(memberTokenErrorUpdate.getRefreshToken())
                        , pfMemberToken.isUse.eq(true)
                    );
                } else {
                    clause.set(pfMemberToken.modId, memberTokenErrorUpdate.getAccessToken());
                    clause.where(pfMemberToken.accessToken.eq(memberTokenErrorUpdate.getAccessToken())
                        , pfMemberToken.isUse.eq(true)
                    );
                }
        clause.execute();

    }

}
