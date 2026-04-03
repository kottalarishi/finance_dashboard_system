package com.rishi.finance_dashboard_system.dto;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class createUserRegistration {

    @NotBlank
    private String userName;

    @Email
    @NotBlank
    private String email;

    @NotBlank
//    @Pattern(regexp = "^[0-9]{10}$",message = "phone number must  be 10 digits")
    private String phoneNumber;

    @NotBlank
//    @Pattern(regexp ="^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[$@%!?&])[a-zA-Z\\d*$@%!?&]{8,}$",
//            message = "Password must at least 8 characters with lowercase, uppercase,special character,and number"      )
    private String password;


}
