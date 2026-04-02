package com.rishi.finance_dashboard_system.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import jakarta.validation.constraints.Pattern;
import lombok.*;

import java.util.List;


@Entity
@Table(name="users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {
     @Id
     @GeneratedValue(strategy = GenerationType.IDENTITY)
     @Column(name="id")
     private Long id;

     @Column(name="userName")

     private String userName;


     @Column(name="userEmail")
     private String email;


     @Column(name="phoneNumber")
     private String phoneNumber;

     @Column(name="password")
     private String password;


     @Column(name="is_active",nullable = false)
     private boolean is_active;

     @Enumerated(EnumType.STRING)
     private Role role;

     @OneToMany(mappedBy = "user")
     private List<Transaction> transactionList;


}
