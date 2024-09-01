package com.terry.demo.domain.member.membertoken.repository;

import com.terry.demo.core.entity.PfMemberToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberTokenRepository extends JpaRepository<PfMemberToken, String> {

    PfMemberToken findByIdEmailAndAccessTokenAndIsUse(String idEmail, String accessToken, Boolean isUse);

    PfMemberToken findByIdEmailAndRefreshToken(String idEmail, String refreshToken);

}
