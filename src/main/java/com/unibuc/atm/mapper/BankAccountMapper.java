package com.unibuc.atm.mapper;

import com.unibuc.atm.dto.BankAccountRequest;
import com.unibuc.atm.model.BankAccount;
import org.springframework.stereotype.Component;

@Component
public class BankAccountMapper {

    public BankAccount bankAccountRequestToBankAccount(BankAccountRequest bankAccountRequest) {
        return new BankAccount(bankAccountRequest.getAccountNumber(), bankAccountRequest.getBalance(),
                bankAccountRequest.getType(), bankAccountRequest.getCardNumber());
    }
}
