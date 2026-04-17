package com.rishi.finance_dashboard_system.dto;


import com.rishi.finance_dashboard_system.entity.Type;
import com.rishi.finance_dashboard_system.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.cglib.core.Local;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
public class TransactionResponse {

         private Long id;

         private BigDecimal amount;

         private Type type;

         private String category;

         private LocalDate date;

         private String notes;

         private String userName;

         private Long userId;


}
