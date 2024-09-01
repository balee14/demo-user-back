package com.terry.demo.core.entity;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class PfMemberPrincipal implements UserDetails {

    private final PfMember pfMember;

    public PfMemberPrincipal(PfMember pfMember){
        this.pfMember = pfMember;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorities = new ArrayList<>();

        // Extract list of permissions (name)
        this.pfMember.getAuthorities().stream().map(authority -> new SimpleGrantedAuthority(authority.getAuthorityName()))
//            .forEach(authorities::add);
            .collect(Collectors.toList());

//        List<GrantedAuthority> grantedAuthorities = pfMember.getAuthorities().stream()
//            .map(authority -> new SimpleGrantedAuthority(authority.getAuthorityName()))
//            .collect(Collectors.toList());

        // Extract list of roles (ROLE_name)
//        this.pfMember.getAuthorities().forEach(p -> {
//            GrantedAuthority authority = new SimpleGrantedAuthority("ROLE_" + p);
//            authorities.add(authority);
//        });

        return authorities;
    }

    @Override
    public String getPassword() {
        return this.pfMember.getPwd();
    }

    @Override
    public String getUsername() {
        return this.pfMember.getIdEmail();
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
