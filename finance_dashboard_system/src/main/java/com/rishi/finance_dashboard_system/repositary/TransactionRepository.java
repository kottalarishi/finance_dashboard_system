package com.rishi.finance_dashboard_system.repositary;

import com.rishi.finance_dashboard_system.entity.Transaction;
import com.rishi.finance_dashboard_system.entity.Type;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;


public interface TransactionRepository extends JpaRepository<Transaction,Long> {

    List<Transaction> findByUserId(Long id);

    List<Transaction> findByUserIdAndCategoryAndTypeAndDate(
       Long userId,
       String category,
       Type type,
       LocalDate startDate,
       LocalDate endDate

    );
}
