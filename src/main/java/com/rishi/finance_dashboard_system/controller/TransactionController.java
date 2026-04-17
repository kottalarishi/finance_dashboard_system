package com.rishi.finance_dashboard_system.controller;

import com.rishi.finance_dashboard_system.dto.TransactionResponse;
import com.rishi.finance_dashboard_system.dto.UpdateTransactionRequest;
import com.rishi.finance_dashboard_system.dto.CreateTransactionRequest;
import com.rishi.finance_dashboard_system.entity.Type;
import com.rishi.finance_dashboard_system.serviceImplementation.TransactionInterfaceImplementation;
import com.rishi.finance_dashboard_system.util.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/transactions")
public class TransactionController {

    @Autowired
    public final TransactionInterfaceImplementation interfaceImplementation;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/addTransaction")
    public ResponseEntity<ApiResponse<TransactionResponse>> addTransaction(@Valid @RequestBody CreateTransactionRequest transactionRequest){

        TransactionResponse transactionResponse= interfaceImplementation.addTransaction(transactionRequest);

        ApiResponse<TransactionResponse> response= new ApiResponse<>(201,"Transaction done successfully",transactionResponse);

        return ResponseEntity.status(response.getStatusCode()).body(response);
    }
    @PreAuthorize("hasAnyRole('ADMIN','ANALYST')")
    @GetMapping("/fetchByID/{id}")
    public ResponseEntity<ApiResponse<TransactionResponse>> getTransactionById(@PathVariable Long id){

        TransactionResponse transactionResponse= interfaceImplementation.getTransactionById(id);
        ApiResponse<TransactionResponse> response= new ApiResponse<>(200,"Transaction fetched successfully",transactionResponse);
        return ResponseEntity.status(response.getStatusCode()).body(response);

    }
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse<TransactionResponse>> deleteTransactionById(@PathVariable Long id){
        interfaceImplementation.deleteTransactionById(id);

        ApiResponse<TransactionResponse> response= new ApiResponse<>(200,"Transaction deleted successfully",null);
        return ResponseEntity.status(response.getStatusCode()).body(response);

    }
    @PreAuthorize("hasAnyRole('ADMIN','ANALYST')")
    @GetMapping("/getTransactions/{userId}")
    public ResponseEntity<ApiResponse<List<TransactionResponse>>> getUserTransactionsById(@PathVariable  Long userId){

        List<TransactionResponse> transactionResponses= interfaceImplementation.getUserTransactionsById(userId);
        ApiResponse<List<TransactionResponse>> response= new ApiResponse<>(200,"Transactions fetched successfully",transactionResponses);
        return  ResponseEntity.status(response.getStatusCode()).body(response);
    }
    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping("/partialupdate/{id}")
    public ResponseEntity<ApiResponse<TransactionResponse>> updateTransaction( @PathVariable Long id,     @RequestBody UpdateTransactionRequest transactionRequest){

           TransactionResponse transactionResponse= interfaceImplementation.updateTransaction(id,transactionRequest);
           ApiResponse<TransactionResponse> response= new ApiResponse<>(200,"Transaction updated Successfully",transactionResponse);
        return  ResponseEntity.status(response.getStatusCode()).body(response);
    }
    @PreAuthorize("hasAnyRole('ADMIN','ANALYST')")
    @GetMapping("/filterTransactions")
    public  ResponseEntity<ApiResponse<List<TransactionResponse>>>
                       searchTransactions(@RequestParam Long userId,
                       @RequestParam(required = false) String category,
                       @RequestParam(required = false) Type type,
                       @RequestParam(required = false)LocalDate startDate,
                       @RequestParam(required = false) LocalDate endDate
                                                    ){

        List<TransactionResponse> transactionResponses= interfaceImplementation.
                searchTransactions(userId,category,type,startDate,endDate);

        ApiResponse<List<TransactionResponse>> response= new ApiResponse<>(200,"Transactions fetched successfully",transactionResponses);

        return  ResponseEntity.status(response.getStatusCode()).body(response);
    }

}
