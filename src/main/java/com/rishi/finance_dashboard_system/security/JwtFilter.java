package com.rishi.finance_dashboard_system.security;

import com.rishi.finance_dashboard_system.entity.User;
import com.rishi.finance_dashboard_system.repositary.UserRepository;
import com.rishi.finance_dashboard_system.service.CustomerUserDetailsService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {


    private  final JwtUtil jwtUtil;
    private final UserRepository userRepository;

    private final CustomerUserDetailsService customerUserDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        final String authHeader=request.getHeader("Authorization");
        String token=null;

        if(authHeader!=null && authHeader.startsWith("Bearer ")){
            token=authHeader.substring(7);
        }

        if(token!=null && jwtUtil.validateToken(token)){
            String email=jwtUtil.getEmailFromToken(token);
            User user=userRepository.findByEmail(email).orElse(null);
            if(user!=null && SecurityContextHolder.getContext().getAuthentication()==null){
                CustomUserDetails customUserDetails= (CustomUserDetails) customerUserDetailsService.loadUserByUsername(email);

                UsernamePasswordAuthenticationToken authenticationToken=
                        new UsernamePasswordAuthenticationToken(
                                customUserDetails,
                                null,
                                customUserDetails.getAuthorities()
                        );
                authenticationToken.setDetails(user.getId());
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);


            }
        }
        filterChain.doFilter(request, response);

    }

}
