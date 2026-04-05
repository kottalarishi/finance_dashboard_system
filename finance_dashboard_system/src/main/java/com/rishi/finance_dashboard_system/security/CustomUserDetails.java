package com.rishi.finance_dashboard_system.security;

import com.rishi.finance_dashboard_system.entity.User;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.List;


@RequiredArgsConstructor
public class CustomUserDetails implements UserDetails {

    @Autowired
    private final User user;

    private  final Collection<? extends GrantedAuthority> grantedAuthorities;



    @Override
    @NonNull
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return grantedAuthorities;
    }

    @Override
    @Nullable
    public  String getPassword() {
        return user.getPassword();
    }

    @Override
    @NonNull
    public String getUsername() {
        return user.getEmail();
    }

    @Override
    public boolean isAccountNonLocked() {
        return user.is_active();
    }

    @Override
    public boolean isEnabled() {
        return user.is_active();
    }

     public User user(){
        return  user;
     }

}
