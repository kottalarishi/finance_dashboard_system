package com.rishi.finance_dashboard_system.repositary;

import com.rishi.finance_dashboard_system.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


public interface UserRepository  extends JpaRepository<User, Long> {
   boolean existsByEmail(String userEmail);

}
