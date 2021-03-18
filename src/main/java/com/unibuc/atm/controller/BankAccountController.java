package com.unibuc.atm.controller;

import com.unibuc.atm.dto.BankAccountRequest;
import com.unibuc.atm.dto.TransferRequest;
import com.unibuc.atm.mapper.BankAccountMapper;
import com.unibuc.atm.model.BankAccount;
import com.unibuc.atm.service.BankAccountService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;

@RestController
@Validated
@RequestMapping("/bankAccounts")
public class BankAccountController {

    private BankAccountService bankAccountService;
    private BankAccountMapper bankAccountMapper;

    public BankAccountController(BankAccountService bankAccountService, BankAccountMapper bankAccountMapper) {
        this.bankAccountService = bankAccountService;
        this.bankAccountMapper = bankAccountMapper;
    }

    @PostMapping
    public ResponseEntity<BankAccount> createBankAccount(
            @Valid
            @RequestBody BankAccountRequest bankAccountRequest) {
        BankAccount bankAccount = bankAccountMapper.bankAccountRequestToBankAccount(bankAccountRequest);
        BankAccount createdBankAccount = bankAccountService.createBankAccount(bankAccount);
        return ResponseEntity
                .created(URI.create("/bankAccount/" + createdBankAccount.getId()))
                .body(createdBankAccount);
    }

    @GetMapping("/{id}")
    public BankAccount getBankAccount(@PathVariable Long id) {
        return bankAccountService.getBankAccount(id);
    }

    @PutMapping
    public void makeBankAccountTransfer(
            @Valid
            @RequestBody
            TransferRequest transferRequest) {
        bankAccountService.makeBankAccountTransfer(transferRequest);
    }









}
