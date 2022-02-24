package com.task.banking.controller;

import com.task.banking.entity.Account;
import com.task.banking.entity.dto.AccountDto;
import com.task.banking.entity.dto.CashFlowDto;
import com.task.banking.entity.dto.TransferDto;
import com.task.banking.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/account")
public class AccountController {

    @Autowired
    private AccountService accountService;

    @GetMapping()
    public ResponseEntity<List<Account>> getAllAccount() {
        return new ResponseEntity(accountService.getAll(), HttpStatus.OK);
    }

    @GetMapping("/balance/{id}")
    public ResponseEntity<Double> getBalance(@PathVariable("id") Long id) {
        return new ResponseEntity<>(accountService.getBalance(id), HttpStatus.OK);
    }

    @PostMapping("/open")
    public ResponseEntity openAccount(@RequestBody AccountDto accountDto) {
        accountService.openAccount(accountDto);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @PostMapping("/deposit")
    public ResponseEntity deposit(@RequestBody CashFlowDto cashFlowDto) {
        accountService.deposit(cashFlowDto);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @PostMapping("/withdraw")
    public ResponseEntity withdraw(@RequestBody CashFlowDto cashFlowDto) {
        accountService.withdraw(cashFlowDto);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @PostMapping("/transfer")
    public ResponseEntity<String> transfer(@RequestBody TransferDto transferDto) {
        accountService.transfer(transferDto);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }


}
