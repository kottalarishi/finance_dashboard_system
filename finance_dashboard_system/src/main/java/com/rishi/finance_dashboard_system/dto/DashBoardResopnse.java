package com.rishi.finance_dashboard_system.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DashBoardResopnse{

    private BigDecimal totalIncome;
    private BigDecimal totalExpenses;
    private BigDecimal netBalance;
    private Map<String ,BigDecimal> categoryWiseTotal;
    private Map<String,BigDecimal> monthlyTrends;
    private List<TransactionResponse> recentActivity;
    private Map<String,Map<String,BigDecimal>> monthlyCategoryWiseTotal;
}
