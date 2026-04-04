package com.rishi.finance_dashboard_system.serviceInterfaces;

import com.rishi.finance_dashboard_system.dto.TransactionResponse;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public interface DashBoradInterface {

    BigDecimal getTotalIncome(Long userId);

    BigDecimal getTotalExpenses(Long userId);

    BigDecimal getNetBalance(Long userId);

    Map<String ,BigDecimal> getCategoryWiseTotal(Long userId);

    Map<String,BigDecimal>  getMonthlyTrends(Long userId);

    List<TransactionResponse> getRecentActivity(Long userId);

    Map<String,Map<String,BigDecimal>> getMonthlyCategoryWiseTotal(Long userId);

}
