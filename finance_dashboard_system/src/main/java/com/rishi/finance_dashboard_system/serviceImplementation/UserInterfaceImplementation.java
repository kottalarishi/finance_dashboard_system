package com.rishi.finance_dashboard_system.serviceImplementation;

import com.rishi.finance_dashboard_system.dto.UpdateUserRequest;
import com.rishi.finance_dashboard_system.dto.UserResponse;
import com.rishi.finance_dashboard_system.dto.CreateUserRegistration;
import com.rishi.finance_dashboard_system.entity.Role;
import com.rishi.finance_dashboard_system.entity.User;
import com.rishi.finance_dashboard_system.exception.GlobalExceptionHandler;
import com.rishi.finance_dashboard_system.mapper.UserMapper;
import com.rishi.finance_dashboard_system.repositary.UserRepository;
import com.rishi.finance_dashboard_system.serviceInterfaces.UserInterface;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Locale;

@Service
@RequiredArgsConstructor
public class UserInterfaceImplementation implements UserInterface {

    @Autowired
    private  final UserRepository userRepository;
    @Autowired
    private final UserMapper userMapper;

    @Autowired
    private final GlobalExceptionHandler exceptionHandler;
    @Autowired
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserResponse addUser(CreateUserRegistration userRegistration) {

        if(userRegistration==null){
            throw new IllegalArgumentException("Invalid Input");
        }

        if(userRepository.existsByEmail(userRegistration.getEmail())){
            throw  new IllegalArgumentException("Email already exists");
        }
        if(userRegistration.getPhoneNumber()==null || !userRegistration.getPhoneNumber().matches("^[0-9]{10}$")){
            throw  new IllegalArgumentException("phoneNumber must consists of 10 digits");
        }

        String password=userRegistration.getPassword();
        if(password.length()<8){
            throw new IllegalArgumentException("password must be 8 characters");
        }
        if(!password.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[$@%!?&])[a-zA-Z\\d*$@%!?&]{8,}$")){
            throw new IllegalArgumentException("Password must at least 8 characters with lowercase, uppercase,special character,and number" );

        }

        User user= userMapper.toEntity(userRegistration);

        user.setRole(Role.VIEWER);
        user.set_active(true);
        user.setPassword(passwordEncoder.encode(user.getPassword()));

      User saveUser=  userRepository.save(user);
        return  userMapper.toDto(saveUser);
    }

    @Override
    public UserResponse getUserById(Long id) {
        User user=userRepository.findById(id).orElseThrow(()-> new
                GlobalExceptionHandler.ResourceNotFoundException("id not found with "+ id));
        return userMapper.toDto(user);
    }

    @Override
    public void deleteUserById(Long id) {
        User user=userRepository.findById(id).orElseThrow(()-> new
                GlobalExceptionHandler.ResourceNotFoundException("id not found with "+ id));

        userRepository.deleteById(id);
    }

    @Override
    public UserResponse updateUser(Long id, UpdateUserRequest updateUserRequest) {

        User user=userRepository.findById(id).orElseThrow(()-> new
                GlobalExceptionHandler.ResourceNotFoundException("id not found with "+ id));

        if(updateUserRequest.getUserName()!=null){
            user.setUserName(updateUserRequest.getUserName());
        }
        if(updateUserRequest.getPassword()!=null){

            user.setPassword(updateUserRequest.getPassword());

        }

        if(updateUserRequest.getPhoneNumber()!=null){
            user.setPhoneNumber(updateUserRequest.getPhoneNumber());
        }

        if(updateUserRequest.getEmail()!=null){
            user.setEmail(updateUserRequest.getEmail());
        }

        User updateUser= userRepository.save(user);


        return userMapper.toDto(updateUser);
    }

    @Override
    public String updateUserRole(Long userId, String role) {

        User user=userRepository.findById(userId).orElseThrow(()-> new
                GlobalExceptionHandler.ResourceNotFoundException("id not found with "+ userId));

          if(role==null){
              throw new IllegalArgumentException("Invalid Input");
          }

          Role newRole= Role.valueOf(role.toUpperCase(Locale.ROOT));

          user.setRole(newRole);


        userRepository.save(user);


        return "Role updated successfully" +" "+ role ;
    }
}
