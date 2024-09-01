package com.terry.demo.core.util;

import com.terry.demo.core.dto.CommonCustomType;
import com.terry.demo.core.dto.CommonRuntimeException;
import com.terry.demo.core.entity.PfAuthority;
import com.terry.demo.core.entity.PfMemberPrincipal;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.ObjectUtils;

import java.util.List;
import java.util.stream.Collectors;

@Log4j2
public class PfMemberUtil {

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

        PfMemberPrincipal pfMemberPrincipal = (PfMemberPrincipal) authentication.getPrincipal();
        return memberId = pfMemberPrincipal.getPfMember().getMemberId();

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

        PfMemberPrincipal pfMemberPrincipal = (PfMemberPrincipal) authentication.getPrincipal();
        memberName = pfMemberPrincipal.getPfMember().getName();

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
        PfMemberPrincipal pfMemberPrincipal = (PfMemberPrincipal) authentication.getPrincipal();
        return pfMemberPrincipal.getPfMember().getAuthorities().stream()
            .map(PfAuthority::getAuthorityName)
            .collect(Collectors.toList());

    }



}
