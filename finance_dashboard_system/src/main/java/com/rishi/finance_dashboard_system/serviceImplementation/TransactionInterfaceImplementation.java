package com.rishi.finance_dashboard_system.serviceImplementation;

import com.rishi.finance_dashboard_system.dto.TransactionResponse;
import com.rishi.finance_dashboard_system.dto.UpdateTransactionRequest;
import com.rishi.finance_dashboard_system.dto.CreateTransactionRequest;
import com.rishi.finance_dashboard_system.entity.Transaction;
import com.rishi.finance_dashboard_system.entity.Type;
import com.rishi.finance_dashboard_system.entity.User;
import com.rishi.finance_dashboard_system.exception.GlobalExceptionHandler;
import com.rishi.finance_dashboard_system.mapper.TransactionMapper;
import com.rishi.finance_dashboard_system.repositary.TransactionRepository;
import com.rishi.finance_dashboard_system.repositary.UserRepository;
import com.rishi.finance_dashboard_system.serviceInterfaces.TransactionInterface;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class TransactionInterfaceImplementation implements TransactionInterface {

    @Autowired
    public final TransactionRepository transactionRepository;

    @Autowired
    public  final  UserRepository userRepository;

    @Autowired
    private final GlobalExceptionHandler exceptionHandler;

    @Autowired
     private final  TransactionMapper transactionMapper;

    @Override
    public TransactionResponse addTransaction(CreateTransactionRequest transactionRequest) {
        if(transactionRequest==null){
            throw new IllegalArgumentException("Invalid Input");
        }
        if(transactionRequest.getAmount().compareTo(BigDecimal.ZERO)<=0){
            throw new IllegalArgumentException("Amount should be positive and greater than zero");
        }
        User user= userRepository.findById(transactionRequest.getUser_id())
                .orElseThrow(()-> new GlobalExceptionHandler.ResourceNotFoundException("id not found with "+ transactionRequest.getUser_id()));


        Transaction transaction =  transactionMapper.toEntity(transactionRequest);
        transaction.setCategory(transactionRequest.getCategory().toUpperCase());
        transaction.setUser(user);

       Transaction savetransaction= transactionRepository.save(transaction);


        return transactionMapper.toDto(savetransaction);


    }

    @Override
    public TransactionResponse getTransactionById(Long id) {

       Transaction transaction = transactionRepository.findById(id).
               orElseThrow(()-> new GlobalExceptionHandler.ResourceNotFoundException("Transaction not found with"+ id));
        return transactionMapper.toDto(transaction);

    }

    @Override
    public void deleteTransactionById(Long id) {
        Transaction transaction = transactionRepository.findById(id).
                orElseThrow(()-> new GlobalExceptionHandler.ResourceNotFoundException("Transaction not found with"+ id));
           transactionRepository.deleteById(id);
    }

    @Override
    public List<TransactionResponse> getUserTransactionsById(Long userId) {
         userRepository.findById(userId)
                .orElseThrow(()-> new GlobalExceptionHandler.ResourceNotFoundException("id not found with "+ userId));

          List<Transaction> list= transactionRepository.findByUserId(userId);
          return list.stream().map(transactionMapper::toDto).toList();

    }

    @Override
    public List<TransactionResponse> searchTransactions(Long userId,
                                                        String category,
                                                        Type type,
                                                        LocalDate startDate,
                                                        LocalDate endDate) {
        userRepository.findById(userId)
                .orElseThrow(()-> new GlobalExceptionHandler.ResourceNotFoundException("id not found with "+ userId));
        List<Transaction> list= transactionRepository.filterTransaction(userId,category,type,startDate,endDate);
        return list.stream().map(transactionMapper::toDto).collect(Collectors.toList());
    }

    @Override
    public TransactionResponse updateTransaction(Long id, UpdateTransactionRequest updateTransactionRequest) {
          if(updateTransactionRequest == null){
              throw new IllegalArgumentException("Invalid Input");
          }


         Transaction transaction = transactionRepository.findById(id)
                 .orElseThrow(()->new GlobalExceptionHandler.ResourceNotFoundException("transaction Not found with id "+id));
          if(updateTransactionRequest.getCategory()!=null){
              transaction.setCategory(updateTransactionRequest.getCategory());
          }
          if(updateTransactionRequest.getType()!=null){
              transaction.setType(updateTransactionRequest.getType());
          }
          if(updateTransactionRequest.getNotes()!=null){
              transaction.setNotes(updateTransactionRequest.getNotes());
          }
         Transaction  updatedTransaction=  transactionRepository.save(transaction);
        return transactionMapper.toDto(updatedTransaction);
    }
}
