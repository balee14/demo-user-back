package com.terry.demo.domain.member.member.service;

import com.terry.demo.core.dto.CommonCustomType;
import com.terry.demo.core.dto.CommonRuntimeException;
import com.terry.demo.core.entity.PfAuthority;
import com.terry.demo.core.entity.PfMember;
import com.terry.demo.core.util.PfMemberUtil;
import com.terry.demo.domain.member.member.dto.MemberDto;
import com.terry.demo.domain.member.member.dto.MemberPwdUpdateRequest;
import com.terry.demo.domain.member.member.dto.MemberUpdateRequest;
import com.terry.demo.domain.member.member.dto.list.MemberListRequest;
import com.terry.demo.domain.member.member.dto.list.MembersDto;
import com.terry.demo.domain.member.member.repository.MemberRepository;
import com.terry.demo.domain.member.member.repository.MemberRepositoryCustom;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;

@Log4j2
@Service
@Transactional
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final MemberRepositoryCustom memberRepositoryCustom;
    private final PasswordEncoder passwordEncoder;

    /**
     * member 목록 조회
     *
     * @param
     * @return
     */
    @Transactional(readOnly = true)
    public Page<MembersDto> getMemberList(MemberListRequest memberListRequest, Pageable pageable) {
        return memberRepositoryCustom.getMemberList(memberListRequest, pageable);
    }

    /**
     * member 조회
     * @param
     * @return
     */
    @Transactional(readOnly = true)
    public MemberDto getMemberById(Long memberId) {
        return memberRepositoryCustom.getMemberById(memberId);
    }

    /**
     *
     * @param pfMember
     * @return
     */
    public PfMember memberSave(PfMember pfMember) {

        // 유저체크
        if (memberRepository.findByIdEmail(pfMember.getIdEmail()).orElse(null) != null) {
            throw new CommonRuntimeException(CommonCustomType.SUCCESSFUL_200_2.name());
        }

        // 패스워드 암호화
        pfMember.setPwd(passwordEncoder.encode(pfMember.getPwd()));

        // 권한 설정
        PfAuthority authority;
        // 어드민 사용자
        authority = PfAuthority.builder()
            .authorityName("ROLE_USER")
            .build();
//        if (pfMember.getIsMemberAdmin()) {
//            // 어드민 사용자
//            authority = PfAuthority.builder()
//                .authorityName("ROLE_ADMIN")
//                .build();
//        } else {
//            // 파트너사 사용자
//            authority = PfAuthority.builder()
//                .authorityName("ROLE_PARTNER")
//                .build();
//        }
        pfMember.setAuthorities(Collections.singleton(authority));
        return memberRepository.save(pfMember);

    }

    /**
     *
     * @param memberUpdateRequest
     * @return
     */
    public Long memberUpdate(MemberUpdateRequest memberUpdateRequest) {

        memberUpdateRequest.setModId(PfMemberUtil.getIdEmail());
        return memberRepositoryCustom.memberUpdate(memberUpdateRequest);

    }

    /**
     *
     * @param memberPwdUpdateRequest
     * @return
     */
    public Long memberPwdUpdate(MemberPwdUpdateRequest memberPwdUpdateRequest) {

        // 패스워드 암호화
        memberPwdUpdateRequest.setPwd(passwordEncoder.encode(memberPwdUpdateRequest.getPwd()));
        memberPwdUpdateRequest.setModId(PfMemberUtil.getIdEmail());
        return memberRepositoryCustom.memberPwdUpdate(memberPwdUpdateRequest);

    }



}

