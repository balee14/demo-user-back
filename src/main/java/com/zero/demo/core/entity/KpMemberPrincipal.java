package com.zero.demo.core.entity;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class KpMemberPrincipal implements UserDetails {

    private final KpMember kpMember;

    public KpMemberPrincipal(KpMember kpMember){
        this.kpMember = kpMember;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorities = new ArrayList<>();

        // Extract list of permissions (name)
        this.kpMember.getAuthorities().stream().map(authority -> new SimpleGrantedAuthority(authority.getAuthorityName()))
//            .forEach(authorities::add);
            .collect(Collectors.toList());

//        List<GrantedAuthority> grantedAuthorities = kpMember.getAuthorities().stream()
//            .map(authority -> new SimpleGrantedAuthority(authority.getAuthorityName()))
//            .collect(Collectors.toList());

        // Extract list of roles (ROLE_name)
//        this.kpMember.getAuthorities().forEach(p -> {
//            GrantedAuthority authority = new SimpleGrantedAuthority("ROLE_" + p);
//            authorities.add(authority);
//        });

        return authorities;
    }

    @Override
    public String getPassword() {
        return this.kpMember.getPwd();
    }

    @Override
    public String getUsername() {
        return this.kpMember.getIdEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
