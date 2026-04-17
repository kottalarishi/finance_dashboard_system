package com.rishi.finance_dashboard_system.service;


import com.rishi.finance_dashboard_system.entity.User;
import com.rishi.finance_dashboard_system.repositary.UserRepository;
import com.rishi.finance_dashboard_system.security.CustomUserDetails;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerUserDetailsService implements UserDetailsService {

    @Autowired
    private  final UserRepository userRepository;

    @Override
    @NonNull
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email)
                .orElseThrow(()-> new UsernameNotFoundException("User not found"));

        String role= user.getRole().name();
        List<GrantedAuthority> grantedAuthorityList= List.of(new SimpleGrantedAuthority("ROLE_"+ role));



        return new CustomUserDetails(user,grantedAuthorityList);
    }
}
