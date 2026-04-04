package com.rishi.finance_dashboard_system.serviceImplementation;

import com.rishi.finance_dashboard_system.dto.TransactionResponse;
import com.rishi.finance_dashboard_system.entity.Transaction;
import com.rishi.finance_dashboard_system.entity.Type;
import com.rishi.finance_dashboard_system.exception.GlobalExceptionHandler;
import com.rishi.finance_dashboard_system.mapper.TransactionMapper;
import com.rishi.finance_dashboard_system.repositary.TransactionRepository;
import com.rishi.finance_dashboard_system.repositary.UserRepository;
import com.rishi.finance_dashboard_system.serviceInterfaces.DashBoradInterface;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DashBoardInterfaceImplementation implements DashBoradInterface {
    @Autowired
    public  final UserRepository userRepository;
    @Autowired
    public final TransactionRepository transactionRepository;

    @Autowired
    public final TransactionMapper transactionMapper;

    @Override
    public BigDecimal getTotalIncome(Long userId) {
         userRepository.findById(userId).
                 orElseThrow(()-> new GlobalExceptionHandler.
                         ResourceNotFoundException("No user found with id "+userId));
         List<Transaction> transactions=  transactionRepository.findByUserId(userId);
         BigDecimal totalIncome= BigDecimal.ZERO;
         for(Transaction transaction:transactions){
             if(transaction.getType()== Type.INCOME){
                 totalIncome=totalIncome.add(transaction.getAmount());
             }
         }

        return totalIncome;
    }

    @Override
    public BigDecimal getTotalExpenses(Long userId) {

        userRepository.findById(userId).
                orElseThrow(()-> new GlobalExceptionHandler.
                        ResourceNotFoundException("No user found with id "+userId));
        List<Transaction> transactions=  transactionRepository.findByUserId(userId);
        BigDecimal totalExpenses= BigDecimal.ZERO;
        for(Transaction transaction:transactions){
            if(transaction.getType()==Type.EXPENSE){
                totalExpenses=totalExpenses.add(transaction.getAmount());
            }
        }

        return totalExpenses;
    }

    @Override
    public BigDecimal getNetBalance(Long userId) {
        userRepository.findById(userId).
                orElseThrow(()-> new GlobalExceptionHandler.
                        ResourceNotFoundException("No user found with id "+userId));
        BigDecimal netBalance=BigDecimal.ZERO;
        BigDecimal totalIncome= getTotalIncome(userId);
        BigDecimal totalExpenses= getTotalExpenses(userId);

        netBalance=totalIncome.subtract(totalExpenses);



        return netBalance;
    }

    @Override
    public Map<String, BigDecimal> getCategoryWiseTotal(Long userId) {

        userRepository.findById(userId).
                orElseThrow(()-> new GlobalExceptionHandler.
                        ResourceNotFoundException("No user found with id "+userId));
        List<Transaction> transactions=  transactionRepository.findByUserId(userId);

        return transactions.stream().collect(Collectors.groupingBy(
                Transaction::getCategory,Collectors.reducing(BigDecimal.ZERO,
                        Transaction::getAmount,
                        BigDecimal::add)
        ));
    }

    @Override
    public Map<String, BigDecimal> getMonthlyTrends(Long userId) {

        userRepository.findById(userId).
                orElseThrow(()-> new GlobalExceptionHandler.
                        ResourceNotFoundException("No user found with id "+userId));
        List<Transaction> transactionList=  transactionRepository.findByUserId(userId);

      return  transactionList.stream().collect(Collectors.groupingBy(
                (Transaction t)->t.getDate().getMonth().toString(),
                Collectors.reducing(BigDecimal.ZERO,
                        Transaction::getAmount,
                        BigDecimal::add)
        ));
    }

    @Override
    public List<TransactionResponse> getRecentActivity(Long userId) {
        userRepository.findById(userId).
                orElseThrow(()-> new GlobalExceptionHandler.
                        ResourceNotFoundException("No user found with id "+userId));
          List<Transaction> recentActivity= new ArrayList<>();

        List<Transaction> transactions=  transactionRepository.findByUserId(userId);
          transactions.sort(Comparator.comparing(Transaction::getDate).reversed());

          for(int i=0;i<Math.min(6,transactions.size());i++){
              recentActivity.add(transactions.get(i));
          }

          List<TransactionResponse> transactionResponses= new ArrayList<>();

          for(Transaction transaction:recentActivity){
              transactionResponses.add(transactionMapper.toDto(transaction));
          }

        return transactionResponses;
    }

    @Override
    public Map<String, Map<String, BigDecimal>> getMonthlyCategoryWiseTotal(Long userId) {
        userRepository.findById(userId).
                orElseThrow(()-> new GlobalExceptionHandler.
                        ResourceNotFoundException("No user found with id "+userId));
        List<Transaction> transactions=  transactionRepository.findByUserId(userId);

        return transactions.stream().collect(Collectors.groupingBy((Transaction t)->t.getDate().getMonth().toString(),
                Collectors.groupingBy(Transaction::getCategory,
                Collectors.reducing(BigDecimal.ZERO,
                        Transaction::getAmount,
                        BigDecimal::add))));

    }
}
