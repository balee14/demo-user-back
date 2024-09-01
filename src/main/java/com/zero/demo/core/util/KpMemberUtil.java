package com.zero.demo.core.util;

import com.terry.demo.core.dto.CommonCustomType;
import com.terry.demo.core.dto.CommonRuntimeException;
import com.terry.demo.core.entity.KpAuthority;
import com.terry.demo.core.entity.KpMemberPrincipal;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.ObjectUtils;

import java.util.List;
import java.util.stream.Collectors;

@Log4j2
public class KpMemberUtil {

    /**
     *
     * @return
     */
    public static Long getMemberId() {

        Long memberId = null;

        // authentication객체가 저장되는 시점은 JwtFilter의 doFilter 메소드에서
        // Request가 들어올 때 SecurityContext에 Authentication 객체를 저장해서 사용
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null) {
            //log.debug("Security Context에 인증 정보가 없습니다.");
            throw new CommonRuntimeException(CommonCustomType.CLIENT_ERROR_401_31.name());
        }

        KpMemberPrincipal kpMemberPrincipal = (KpMemberPrincipal) authentication.getPrincipal();
        return memberId = kpMemberPrincipal.getKpMember().getMemberId();

    }

    /**
     * getCurrentMembername 메소드의 역할은 Security Cont
     * @return
     */
    public static String getIdEmail() {

        // authentication객체가 저장되는 시점은 JwtFilter의 doFilter 메소드에서
        // Request가 들어올 때 SecurityContext에 Authentication 객체를 저장해서 사용
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null) {
            //log.debug("Security Context에 인증 정보가 없습니다.");
            throw new CommonRuntimeException(CommonCustomType.CLIENT_ERROR_401_32.name());
        }

        String membername = null;
        if (authentication.getPrincipal() instanceof UserDetails userDetails) {
            membername = userDetails.getUsername();
        } else if (authentication.getPrincipal() instanceof String) {
            membername = (String) authentication.getPrincipal();
        }

        return membername;
    }

    /**
     *
     * @return
     */
    public static String getMemberName() {

        String memberName = "";

        // authentication객체가 저장되는 시점은 JwtFilter의 doFilter 메소드에서
        // Request가 들어올 때 SecurityContext에 Authentication 객체를 저장해서 사용
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null) {
            //log.debug("Security Context에 인증 정보가 없습니다.");
            throw new CommonRuntimeException(CommonCustomType.CLIENT_ERROR_401_33.name());
        }

        KpMemberPrincipal kpMemberPrincipal = (KpMemberPrincipal) authentication.getPrincipal();
        memberName = kpMemberPrincipal.getKpMember().getName();

        return ObjectUtils.isEmpty(memberName)? "" : memberName ;

    }

    /**
     * getCurrentMembername 메소드의 역할은 Security Cont
     *
     * @return
     */
    public static List<String> getMemberAuthority() {

        // authentication객체가 저장되는 시점은 JwtFilter의 doFilter 메소드에서
        // Request가 들어올 때 SecurityContext에 Authentication 객체를 저장해서 사용
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        KpMemberPrincipal kpMemberPrincipal = (KpMemberPrincipal) authentication.getPrincipal();
        return kpMemberPrincipal.getKpMember().getAuthorities().stream()
            .map(KpAuthority::getAuthorityName)
            .collect(Collectors.toList());

    }



}
