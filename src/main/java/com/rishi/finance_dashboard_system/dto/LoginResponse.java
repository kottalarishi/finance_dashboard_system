package com.rishi.finance_dashboard_system.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginResponse {


    private String token;
    private  String email;

    private String role;

}
