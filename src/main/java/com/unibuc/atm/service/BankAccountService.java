package com.unibuc.atm.service;

import com.unibuc.atm.dto.TransferRequest;
import com.unibuc.atm.exception.BankAccountNotFoundException;
import com.unibuc.atm.model.BankAccount;
import com.unibuc.atm.repository.BankAccountRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class BankAccountService {
    private BankAccountRepository bankAccountRepository;

    public BankAccountService(BankAccountRepository bankAccountRepository) {
        this.bankAccountRepository = bankAccountRepository;
    }

    public BankAccount createBankAccount(BankAccount bankAccount) {
        return bankAccountRepository.createBankAccount(bankAccount);
    }

    public BankAccount getBankAccount(Long id) {
        Optional<BankAccount> bankAccountOptional = bankAccountRepository.getBankAccount(id);
        if(bankAccountOptional.isPresent()) {
            return bankAccountOptional.get();
        } else {
            throw new BankAccountNotFoundException(id);
        }
    }

    @Transactional
    public void makeBankAccountTransfer(TransferRequest transferRequest) {
        bankAccountRepository.updateBankAccountBalance(
                transferRequest.getFromBankAccountId(), -transferRequest.getAmount());
        bankAccountRepository.updateBankAccountBalance(
                transferRequest.getToBankAccountId(), transferRequest.getAmount());
    }
}
