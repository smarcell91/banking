package com.task.banking.repository;

import com.task.banking.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {

    @Query("SELECT balance FROM Account WHERE id = ?1")
    double getBalance(Long id);

    @Query("SELECT a FROM Account a WHERE a.name = ?1")
    Optional<Account> findByName(String name);
}
