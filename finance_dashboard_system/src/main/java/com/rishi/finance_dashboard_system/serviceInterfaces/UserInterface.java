package com.rishi.finance_dashboard_system.serviceInterfaces;


import com.rishi.finance_dashboard_system.dto.UpdateUserRequest;
import com.rishi.finance_dashboard_system.dto.UserResponse;
import com.rishi.finance_dashboard_system.dto.createUserRegistration;

public interface UserInterface {

    UserResponse addUser(createUserRegistration userRegistration);

    UserResponse getUserById(Long id);

    void deleteUserById(Long id);

    UserResponse updateUser(Long id, UpdateUserRequest updateUserRequest);


}