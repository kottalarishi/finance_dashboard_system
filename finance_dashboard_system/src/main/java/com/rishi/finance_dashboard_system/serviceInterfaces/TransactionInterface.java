package com.rishi.finance_dashboard_system.serviceInterfaces;

import com.rishi.finance_dashboard_system.dto.TransactionResponse;
import com.rishi.finance_dashboard_system.dto.UpdateTransactionRequest;
import com.rishi.finance_dashboard_system.dto.CreateTransactionRequest;
import com.rishi.finance_dashboard_system.entity.Type;

import java.time.LocalDate;
import java.util.List;

public interface TransactionInterface {
    TransactionResponse addTransaction(CreateTransactionRequest transactionRequest);

    TransactionResponse getTransactionById(Long id);

    void deleteTransactionById(Long id);

    List<TransactionResponse> getUserTransactionsById(Long userId);

    List<TransactionResponse> searchTransactions(
            Long userId,
            String category,
            Type type,
            LocalDate startDate,
            LocalDate endDate

    );
    TransactionResponse updateTransaction(Long id , UpdateTransactionRequest updateTransactionRequest);




}
