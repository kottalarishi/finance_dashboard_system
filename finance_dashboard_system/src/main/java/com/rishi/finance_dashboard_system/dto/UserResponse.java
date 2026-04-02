package com.rishi.finance_dashboard_system.dto;

import com.rishi.finance_dashboard_system.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {

    private Long id;

    private String userName;

    private String email;

    private String phoneNumber;

    private Role role;

    private boolean is_active;


}
