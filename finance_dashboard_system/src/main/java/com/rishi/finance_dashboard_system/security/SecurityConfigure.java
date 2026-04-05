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
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

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

        httpSecurity.csrf(crsf->crsf.disable()).cors(cors->{}).
                exceptionHandling(exception->exception.authenticationEntryPoint(
                        ((request, response, authException) -> {
                            response.setStatus(401);
                            response.setContentType("application/json");
                            response.getWriter().write("{\"statusCode\":401,\"message\":Unauthorized,\"data\":null}");
                        })
                        ).accessDeniedHandler(((request, response, accessDeniedException) -> {
                    response.setStatus(403);
                    response.setContentType("application/json");
                    response.getWriter().write("{\"statusCode\":403,\"message\":Forbidden- insufficient role,\"data\":null}");
                        }))
                ).
                sessionManagement(s->s.sessionCreationPolicy(SessionCreationPolicy.STATELESS)).
                authorizeHttpRequests(auth->auth

                        .requestMatchers("/api/v1/login","/api/v1/users/saveUser").permitAll()
                        .requestMatchers("/api/v1/users/updateRole/**").hasRole("ADMIN")
                        .requestMatchers("/api/v1/users/**").hasRole("ADMIN")
                        .requestMatchers("/api/v1/users/delete/**").hasRole("ADMIN")
                        .requestMatchers("/api/v1/users/partialupdate/**").hasRole("ADMIN")
                        .requestMatchers("/api/v1/users/activateUser/**").hasRole("ADMIN")
                        .requestMatchers("/api/v1/users/deActivateUser/**").hasRole("ADMIN")

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

     @Bean
    public CorsConfigurationSource corsConfigurationSource(){
         CorsConfiguration corsConfiguration= new CorsConfiguration();
         corsConfiguration.setAllowedOrigins(List.of("*"));
         corsConfiguration.setAllowedMethods(List.of("GET","POST","PATCH","PUT","DELETE","OPTIONS"));
         corsConfiguration.setAllowedHeaders(List.of("*"));

         UrlBasedCorsConfigurationSource corsConfigurationSource= new UrlBasedCorsConfigurationSource();
         corsConfigurationSource.registerCorsConfiguration("/**",corsConfiguration);
         return  corsConfigurationSource;

     }



}
