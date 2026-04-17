package com.rishi.finance_dashboard_system.controller;

import com.rishi.finance_dashboard_system.dto.LoginRequest;
import com.rishi.finance_dashboard_system.dto.LoginResponse;
import com.rishi.finance_dashboard_system.security.CustomUserDetails;
import com.rishi.finance_dashboard_system.security.JwtUtil;
import com.rishi.finance_dashboard_system.util.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;


@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class AuthController {


 @Autowired
  private  final AuthenticationManager authenticationManager;
   @Autowired
  private  final JwtUtil jwtUtil;

   @PostMapping("/login")
   public ResponseEntity<ApiResponse<LoginResponse>> login(@RequestBody LoginRequest loginRequest){
  try {
      Authentication authentication = authenticationManager.authenticate(

              new UsernamePasswordAuthenticationToken(
                      loginRequest.getEmail(),
                      loginRequest.getPassword()
              )
      );
      CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();

      assert customUserDetails != null;
      String token = jwtUtil.generateAccessToken(customUserDetails.user());
      String role = Objects.requireNonNull(customUserDetails.getAuthorities().iterator().next().getAuthority()).replace("ROLE_", "");
      String email = customUserDetails.getUsername();
      LoginResponse loginResponse = new LoginResponse(token, email, role);

      ApiResponse<LoginResponse> response = new ApiResponse<>(200, "logged in successfully", loginResponse);


      return ResponseEntity.status(response.getStatusCode()).body(response);
  }catch (BadCredentialsException exception){
      ApiResponse<LoginResponse> response = new ApiResponse<>(401, "invalid credentials ", null);

      return  ResponseEntity.status(response.getStatusCode()).body(response);
  }catch (LockedException exception){
      ApiResponse<LoginResponse> response = new ApiResponse<>(403, "Your account is deActivated  ", null);

      return  ResponseEntity.status(response.getStatusCode()).body(response);

  }
   }

}
