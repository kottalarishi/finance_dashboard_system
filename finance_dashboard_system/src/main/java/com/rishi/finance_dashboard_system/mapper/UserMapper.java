package com.rishi.finance_dashboard_system.mapper;

import com.rishi.finance_dashboard_system.dto.UserResponse;
import com.rishi.finance_dashboard_system.dto.CreateUserRegistration;
import com.rishi.finance_dashboard_system.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

      public   UserResponse toDto(User user){

          if(user==null) {
              return null;
          }
          UserResponse userResponse= new UserResponse();

          userResponse.setId(user.getId());
          userResponse.setUserName(user.getUserName());
          userResponse.setEmail(user.getEmail());
          userResponse.setPhoneNumber(user.getPhoneNumber());
          userResponse.set_active(user.is_active());
          userResponse.setRole(user.getRole());

          return userResponse;

      }

      public   User toEntity(CreateUserRegistration userRegistration){

          if(userRegistration==null){
              return null;
          }

          User user= new User();

          user.setUserName(userRegistration.getUserName());
          user.setEmail(userRegistration.getEmail());
          user.setPhoneNumber(userRegistration.getPhoneNumber());
          user.setPassword(userRegistration.getPassword());



          return user;


      }





}



