package com.rishi.finance_dashboard_system.controller;

import com.rishi.finance_dashboard_system.dto.UserResponse;
import com.rishi.finance_dashboard_system.dto.createUserRegistration;
import com.rishi.finance_dashboard_system.serviceImplementation.UserInterfaceImplementation;
import com.rishi.finance_dashboard_system.util.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class UserController {

    @Autowired
    public final UserInterfaceImplementation userInterfaceImplementation;


   @PostMapping("/saveUser")
    public ResponseEntity<ApiResponse<UserResponse>> addUser(@Valid @RequestBody createUserRegistration userRegistration){
        UserResponse userResponse= userInterfaceImplementation.addUser(userRegistration);

        ApiResponse<UserResponse> response= new ApiResponse<>(201,"user created successfully", userResponse);

        return ResponseEntity.status(response.getStatusCode()).body(response);

    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<UserResponse>> getUserByID(@PathVariable Long id){
       UserResponse userResponse= userInterfaceImplementation.getUserById(id);

       ApiResponse<UserResponse> response= new ApiResponse<>(200,"user fetched successfully",userResponse);

       return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse<UserResponse>>deleteUserByID(@PathVariable Long id){
         userInterfaceImplementation.deleteUserById(id);
        ApiResponse<UserResponse> response= new ApiResponse<>(200,"user deleted successfully",null);
        return ResponseEntity.status(response.getStatusCode()).body(response);

    }




}
