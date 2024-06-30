package com.binarfud.binarfud_service.security.service;

import com.binarfud.binarfud_service.entity.accounts.User;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;

import java.util.List;
import java.util.stream.Collectors;

@Setter
@Getter
public class UserDetailsImpl implements UserDetails {

    private String username;
    private String password;
    private List<GrantedAuthority> authorities;
    private boolean isVerified;

    public UserDetailsImpl(String username, String password, List<GrantedAuthority> authorities, boolean isVerified) {
        this.username = username;
        this.password = password;
        this.authorities = authorities;
        this.isVerified = isVerified;
    }

    public UserDetailsImpl(String username, List<GrantedAuthority> authorities, boolean isVerified) {
        this.username = username;
        this.authorities = authorities;
        this.isVerified = isVerified;
    }

    public static UserDetails build(User user) {
        List<GrantedAuthority> authorities = user.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority(role.getName().name()))
                .collect(Collectors.toUnmodifiableList());
        return new UserDetailsImpl(user.getUsername(), user.getPassword(), authorities, user.isVerified());
    }

    public static UserDetailsImpl build(OidcUser oidcUser) {
        List<GrantedAuthority> authorities = oidcUser.getAuthorities().stream()
                .map(authority -> (GrantedAuthority) authority)
                .collect(Collectors.toList());
        return new UserDetailsImpl(oidcUser.getEmail(), authorities, true);
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
