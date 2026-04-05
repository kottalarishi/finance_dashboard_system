package com.rishi.finance_dashboard_system.security;

import com.rishi.finance_dashboard_system.service.CustomerUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableMethodSecurity
public class SecurityConfigure {

   @Autowired
    private  JwtFilter jwtFilter;
    @Autowired
    private CustomerUserDetailsService customerUserDetailsService;

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();

    }
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration ) throws  Exception{
        return configuration.getAuthenticationManager();
    }


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception{

        httpSecurity.csrf(crsf->crsf.disable()).
                sessionManagement(s->s.sessionCreationPolicy(SessionCreationPolicy.STATELESS)).
                authorizeHttpRequests(auth->auth

                        .requestMatchers("/api/v1/login","/api/v1/users/saveUser").permitAll()
                        .requestMatchers("/api/v1/users/updateRole/**").hasRole("ADMIN")
                        .requestMatchers("/api/v1/users/**").hasRole("ADMIN")
                        .requestMatchers("/api/v1/users/delete/**").hasRole("ADMIN")
                        .requestMatchers("/api/v1/users/partialupdate/**").hasRole("ADMIN")

                        //transaaction apis
                        .requestMatchers("/api/v1/transactions/addTransaction").hasRole("ADMIN")
                        .requestMatchers("/api/v1/transactions/fetchByID/**").hasAnyRole("ADMIN","ANALYST")
                        .requestMatchers("/api/v1/transactions/delete/**").hasRole("ADMIN")
                        .requestMatchers("/api/v1/transactions/getTransactions/**").hasAnyRole("ADMIN","ANALYST")
                        .requestMatchers("/api/v1/transactions/filterTransactions").hasAnyRole("ADMIN","ANALYST")
                        .requestMatchers("/api/v1/transactions/partialupdate/**").hasRole("ADMIN")

                        //dashBoardApis
                        .requestMatchers("/api/v1/dashBoard/totalIncome/**").hasAnyRole("ADMIN","ANALYST","VIEWER")
                        .requestMatchers("/api/v1/dashBoard/totalExpense/**").hasAnyRole("ADMIN","ANALYST","VIEWER")
                        .requestMatchers("/api/v1/dashBoard/netBalance/**").hasAnyRole("ADMIN","ANALYST","VIEWER")
                        .requestMatchers("/api/v1/dashBoard/getCategoryWiseTotal/**").hasAnyRole("ADMIN","ANALYST")
                        .requestMatchers("/api/v1/dashBoard/getMonthlyTrends/**").hasAnyRole("ADMIN","ANALYST")
                        .requestMatchers("/api/v1/dashBoard/getRecentActivity/**").hasAnyRole("ADMIN","ANALYST")
                        .requestMatchers("/api/v1/dashBoard/getMonthlyCategoryWiseTotal/**").hasAnyRole("ADMIN","ANALYST")
                        .anyRequest().authenticated()

                ).userDetailsService(customerUserDetailsService).
                addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return httpSecurity.build();
    }


}
