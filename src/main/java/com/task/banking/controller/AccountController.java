package com.task.banking.controller;

import com.task.banking.entity.Account;
import com.task.banking.entity.AccountDto;
import com.task.banking.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/account")
public class AccountController {

    @Autowired
    private AccountService accountService;

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody AccountDto accountDto) {
        String response = accountService.register(accountDto);
        return new ResponseEntity<String>(response, HttpStatus.OK);
    }

    @PutMapping("/deposit/{accId}/{amount}")
    public ResponseEntity<String> deposit(@PathVariable("accId") Long accId,
                                          @PathVariable("amount") double amount) {
        String response = accountService.deposit(accId, amount);
        return new ResponseEntity<String>(response, HttpStatus.OK);
    }

    @PutMapping("/withdraw/{accId}/{amount}")
    public ResponseEntity<String> withdraw(@PathVariable("accId") Long accId,
                                          @PathVariable("amount") double amount) {
        String response = accountService.withdraw(accId, amount);
        return new ResponseEntity<String>(response, HttpStatus.OK);
    }

    @PutMapping("/transfer/{fromId}/{toId}/{amount}")
    public ResponseEntity<String> withdraw(@PathVariable("fromId") Long fromId,
                                           @PathVariable("toId") Long toId,
                                           @PathVariable("amount") double amount) {
        String response = accountService.transfer(fromId, toId, amount);
        return new ResponseEntity<String>(response, HttpStatus.OK);
    }


}
