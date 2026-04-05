package com.rishi.finance_dashboard_system.serviceInterfaces;


import com.rishi.finance_dashboard_system.dto.UpdateUserRequest;
import com.rishi.finance_dashboard_system.dto.UserResponse;
import com.rishi.finance_dashboard_system.dto.CreateUserRegistration;

public interface UserInterface {

    UserResponse addUser(CreateUserRegistration userRegistration);

    UserResponse getUserById(Long id);

    void deleteUserById(Long id);

    UserResponse updateUser(Long id, UpdateUserRequest updateUserRequest);

    String updateUserRole(Long userId, String role);

    void activateUser(Long userId);

    void deactivateUser(Long userId);
}