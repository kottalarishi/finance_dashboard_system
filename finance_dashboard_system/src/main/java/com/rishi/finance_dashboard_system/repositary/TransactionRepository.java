package com.rishi.finance_dashboard_system.repositary;

import com.rishi.finance_dashboard_system.entity.Transaction;
import com.rishi.finance_dashboard_system.entity.Type;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;


public interface TransactionRepository extends JpaRepository<Transaction,Long> {

    List<Transaction> findByUserId(Long id);

    @Query("select t from Transaction t where t.user.id = :userId " +
            "and (:category is null or t.category = :category) " +
            "and (CAST(:type as string) is null or t.type = :type) " +
            "and (CAST(:startDate as localdate) is null or t.date >= :startDate) " +
            "and (CAST(:endDate as localdate) is null or t.date <= :endDate)")
    List<Transaction> filterTransaction(
       @Param("userId") Long userId,
       @Param("category") String category,
      @Param("type") Type type,
      @Param("startDate") LocalDate startDate,
       @Param("endDate")   LocalDate endDate

    );
}
