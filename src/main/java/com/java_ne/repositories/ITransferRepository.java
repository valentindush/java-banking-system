package com.java_ne.repositories;

import com.java_ne.models.Transfer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ITransferRepository extends JpaRepository<Transfer, Long> {
}
