package com.task.banking.service;

import com.task.banking.entity.Account;
import com.task.banking.entity.dto.AccountDto;
import com.task.banking.entity.dto.CashFlowDto;
import com.task.banking.entity.dto.TransferDto;
import com.task.banking.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;


    public List<Account> getAll() {
        return accountRepository.findAll();
    }

    public double getBalance(Long id) {
        return accountRepository.getBalance(id);
    }

    public void openAccount(AccountDto accountDto) {
        Optional<Account> accountOpt = accountRepository.findByName(accountDto.getName());
        if (accountOpt.isPresent()) {
            throw new NoSuchElementException("account exist with this name: " + accountDto.getName());
        }
        Account account = new Account();
        account.setName(accountDto.getName());
        account.setBalance(accountDto.getAmount());
        accountRepository.save(account);
    }

    @Transactional
    public void deposit(CashFlowDto cashFlowDto) {
        Optional<Account> accountOpt = accountRepository.findById(cashFlowDto.getAccountId());
        if (accountOpt.isEmpty()) {
            throw new NoSuchElementException("account doesn't exist");
        }
        Account account = accountOpt.get();
        account.deposit(cashFlowDto.getAmount());
        accountRepository.save(account);
    }

    @Transactional
    public void withdraw(CashFlowDto cashFlowDto) {
        Optional<Account> accountOpt = accountRepository.findById(cashFlowDto.getAccountId());
        if (accountOpt.isEmpty()) {
            throw new NoSuchElementException("account doesn't exist");
        }
        Account account = accountOpt.get();
        if (account.getBalance() >= cashFlowDto.getAmount()) {
            account.withdraw(cashFlowDto.getAmount());
            accountRepository.save(account);
        }
        else {
            throw new IllegalArgumentException("Not enough money. Max amount that can be withdrawn: " + account.getBalance());
        }
    }

    @Transactional
    public void transfer(TransferDto transferDto) {
        Optional<Account> fromOpt = accountRepository.findById(transferDto.getFromAccountId());
        Optional<Account> toOpt = accountRepository.findById(transferDto.getToAccountId());
        if (fromOpt.isEmpty() || toOpt.isEmpty()) {
            throw new NoSuchElementException("account doesn't exist with " +
                    (fromOpt.isEmpty() ? transferDto.getFromAccountId() : transferDto.getToAccountId()) + " ID");
        }
        Account from = fromOpt.get();
        Account to = toOpt.get();
        if (from.getBalance() >= transferDto.getAmount()) {
            from.withdraw(transferDto.getAmount());
            to.deposit(transferDto.getAmount());
            accountRepository.save(from);
            accountRepository.save(to);
        }
        else {
            throw new IllegalArgumentException("Not enough money. Max amount that can be transferred: " + from.getBalance());
        }
    }
}
