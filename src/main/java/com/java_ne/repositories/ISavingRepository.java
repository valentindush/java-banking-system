package com.java_ne.repositories;

import com.java_ne.models.SavingManagement;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ISavingRepository extends JpaRepository<SavingManagement, Long> {
}
