package com.rishi.finance_dashboard_system.dto;


import com.rishi.finance_dashboard_system.entity.Type;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class createTransactionRequest {


    @NotNull
    private BigDecimal amount;

     private Type type;

    @NotBlank
     private String category;

    @PastOrPresent
    private LocalDate date;
    @NotBlank
    private String notes;

    private Long user_id;
}
