package com.task.banking.service;

import com.task.banking.entity.Account;
import com.task.banking.entity.AccountDto;
import com.task.banking.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;


    public String register(AccountDto accountDto) {
        Optional<Account> accountOpt = accountRepository.findAll().stream()
                .filter(acc -> accountDto.getName().equals(acc.getName()))
                .findAny();
        if (accountOpt.isPresent()) {
            return "name exist";
        }
        Account account = new Account();
        account.setName(accountDto.getName());
        account.setPassword(accountDto.getPassword());
        accountRepository.save(account);
        return account.getName() + " saved";
    }

    public String deposit(Long accId, double amount) {
        Optional<Account> accountOpt = accountRepository.findById(accId);

        if (accountOpt.isEmpty()) {
            return "account doesn't exist";
        }
        Account account = accountOpt.get();
        double balance = account.deposit(amount);
        accountRepository.save(account);
        return amount + " amount added." + " Balance: " + balance;
    }

    public String withdraw(Long accId, double amount) {
        Optional<Account> accountOpt = accountRepository.findById(accId);

        if (accountOpt.isEmpty()) {
            return "account doesn't exist";
        }
        Account account = accountOpt.get();
        double balance = account.withdraw(amount);
        accountRepository.save(account);
        return amount + " amount removed." + " Balance: " + balance;
    }

    @Transactional
    public String transfer(Long fromId, Long toId, double amount) {
        Optional<Account> fromOpt = accountRepository.findById(fromId);
        Optional<Account> toOpt = accountRepository.findById(toId);
        if (fromOpt.isEmpty() || toOpt.isEmpty()) {
            return "account doesn't exist with " + (fromOpt.isEmpty() ? fromId : toId) + " ID";
        }
        Account from = fromOpt.get();
        Account to = toOpt.get();
        double fromBalance = from.withdraw(amount);
        double toBalance = to.deposit(amount);
        return amount + " amount transfered. From " + fromId + " ID, balance: " + fromBalance + ". To " + toId + " ID, balance: " + toBalance;
    }
}
