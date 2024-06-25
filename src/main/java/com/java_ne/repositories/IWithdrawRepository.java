package com.java_ne.repositories;

import com.java_ne.models.WithdrawManagement;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IWithdrawRepository extends JpaRepository<WithdrawManagement, Long> {
}
