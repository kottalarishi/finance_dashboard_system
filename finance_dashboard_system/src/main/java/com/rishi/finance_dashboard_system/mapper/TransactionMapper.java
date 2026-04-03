package com.rishi.finance_dashboard_system.mapper;

import com.rishi.finance_dashboard_system.dto.TransactionResponse;
import com.rishi.finance_dashboard_system.dto.createTransactionRequest;
import com.rishi.finance_dashboard_system.entity.Transaction;
import org.springframework.stereotype.Component;

@Component
public class TransactionMapper {

    public  TransactionResponse toDto(Transaction transaction){

        if(transaction==null){
            return null;
        }

        TransactionResponse transactionResponse= new TransactionResponse();

        transactionResponse.setId(transaction.getId());
        transactionResponse.setUserName(transaction.getUser().getUserName());
        transactionResponse.setAmount(transaction.getAmount());
        transactionResponse.setType(transaction.getType());
        transactionResponse.setCategory(transaction.getCategory());
        transactionResponse.setNotes(transaction.getNotes());
        transactionResponse.setDate(transaction.getDate());

        return  transactionResponse;


    }

    public  Transaction toEntity(createTransactionRequest createTransactionRequest){

        if(createTransactionRequest==null){
            return null;
        }

        Transaction transaction= new Transaction();
        transaction.setNotes(createTransactionRequest.getNotes());
        transaction.setAmount(createTransactionRequest.getAmount());
        transaction.setType(createTransactionRequest.getType());
        transaction.setCategory(createTransactionRequest.getCategory());
        transaction.setDate(createTransactionRequest.getDate());

        return transaction;

    }




}
