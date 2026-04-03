package com.rishi.finance_dashboard_system.serviceImplementation;

import com.rishi.finance_dashboard_system.dto.UserResponse;
import com.rishi.finance_dashboard_system.dto.createUserRegistration;
import com.rishi.finance_dashboard_system.entity.Role;
import com.rishi.finance_dashboard_system.entity.User;
import com.rishi.finance_dashboard_system.exception.GlobalExceptionHandler;
import com.rishi.finance_dashboard_system.mapper.UserMapper;
import com.rishi.finance_dashboard_system.repositary.UserRepository;
import com.rishi.finance_dashboard_system.serviceInterfaces.UserInterface;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserInterfaceImplementation implements UserInterface {

    @Autowired
    private  final UserRepository userRepository;
    @Autowired
    private final UserMapper userMapper;

    @Autowired
    private final GlobalExceptionHandler exceptionHandler;

    @Override
    public UserResponse addUser(createUserRegistration userRegistration) {

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
    public UserResponse updateUser(createUserRegistration userRegistration) {
        return null;
    }
}
