package com.java_ne.repositories;

import com.java_ne.models.Withdraw;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IWithdrawRepository extends JpaRepository<Withdraw, Long> {
}
