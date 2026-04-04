package com.rishi.finance_dashboard_system.controller;

import com.rishi.finance_dashboard_system.dto.TransactionResponse;
import com.rishi.finance_dashboard_system.entity.Transaction;
import com.rishi.finance_dashboard_system.serviceImplementation.DashBoardInterfaceImplementation;
import com.rishi.finance_dashboard_system.util.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/dashBoard")
public class DashBoardController {

     @Autowired
    public final DashBoardInterfaceImplementation interfaceImplementation;

     @GetMapping("/totalIncome/{userId}")
     public ResponseEntity<ApiResponse<BigDecimal>> getTotalIncome(@PathVariable Long userId){
            BigDecimal totalIncome= interfaceImplementation.getTotalIncome(userId);
             ApiResponse<BigDecimal> response= new ApiResponse<>(200,"fetched total income successfully",totalIncome);

             return  ResponseEntity.status(response.getStatusCode()).body(response);
     }

    @GetMapping("/totalExpense/{userId}")
    public ResponseEntity<ApiResponse<BigDecimal>> getTotalExpense(@PathVariable Long userId){
        BigDecimal totalExpenses= interfaceImplementation.getTotalExpenses(userId);
        ApiResponse<BigDecimal> response= new ApiResponse<>(200,"fetched total expense successfully",totalExpenses);

        return  ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @GetMapping("/netBalance/{userId}")
    public ResponseEntity<ApiResponse<BigDecimal>> getTotalNetBalance(@PathVariable Long userId){
        BigDecimal netBalance= interfaceImplementation.getNetBalance(userId);
        ApiResponse<BigDecimal> response= new ApiResponse<>(200,"fetched net balance successfully",netBalance);

        return  ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @GetMapping("/getCategoryWiseTotal/{userId}")
    public ResponseEntity<ApiResponse<Map<String,BigDecimal>>> getCategoryWiseTotal(@PathVariable Long userId){

        Map<String,BigDecimal> map= interfaceImplementation.getCategoryWiseTotal(userId);
        ApiResponse<Map<String,BigDecimal>> response= new ApiResponse<>(200,"fetched CategoryWiseTotal  successfully",map);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @GetMapping("/getMonthlyTrends/{userId}")
    public ResponseEntity<ApiResponse<Map<String,BigDecimal>>> getMonthlyTrends(@PathVariable Long userId){

        Map<String,BigDecimal> map= interfaceImplementation.getMonthlyTrends(userId);
        ApiResponse<Map<String,BigDecimal>> response= new ApiResponse<>(200,"fetched CategoryWiseTotal  successfully",map);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }


    @GetMapping("/getRecentActivity/{userId}")
    public ResponseEntity<ApiResponse<List<TransactionResponse>>> getRecentActivity(@PathVariable Long userId){
         List<TransactionResponse> transactionResponses= interfaceImplementation.getRecentActivity(userId);
         ApiResponse<List<TransactionResponse>> response= new ApiResponse<>(200,"recent activity fetched successfully",transactionResponses);
         return  ResponseEntity.status(response.getStatusCode()).body(response);
    }
    @GetMapping("/getMonthlyCategoryWiseTotal/{userId}")
    public  ResponseEntity<ApiResponse<Map<String, Map<String, BigDecimal>>>> getMonthlyCategoryWiseTotal(@PathVariable Long userId){
        Map<String, Map<String, BigDecimal>>  map= interfaceImplementation.getMonthlyCategoryWiseTotal(userId);
        ApiResponse<Map<String, Map<String, BigDecimal>>> response= new ApiResponse<>(200,"Monthly Category Wise Total fetched successfully",map);
        return  ResponseEntity.status(response.getStatusCode()).body(response);
    }











}


