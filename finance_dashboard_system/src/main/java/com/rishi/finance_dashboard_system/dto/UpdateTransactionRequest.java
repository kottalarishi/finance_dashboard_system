package com.rishi.finance_dashboard_system.dto;

import com.rishi.finance_dashboard_system.entity.Type;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PastOrPresent;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateTransactionRequest {

    private Type type;
    private String category;
    private String notes;


}
