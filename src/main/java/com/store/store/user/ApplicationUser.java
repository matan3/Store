package com.store.store.user;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Set;

public class ApplicationUser implements UserDetails {

    private String password;
    private final String username;
    private Set<? extends GrantedAuthority> grantedAuthorities;
    private final boolean isOwner;

    public ApplicationUser(User user){
        this.username = user.getUserName();
        this.password = user.getPassword();
        this.isOwner = user.getOwner();
    }

    public boolean getIsOwner() {
        return isOwner;
    }
    public void setPassword(String pass) {
        this.password = pass;
    }
    public void setAuthorities(Set<? extends GrantedAuthority> grantedAuthorities) {
        this.grantedAuthorities = grantedAuthorities;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public String getPassword() {
        return password;
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

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return grantedAuthorities;
    }

}
