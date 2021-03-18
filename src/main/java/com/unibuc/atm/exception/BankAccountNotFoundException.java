package com.unibuc.atm.exception;

public class BankAccountNotFoundException extends RuntimeException {

    public BankAccountNotFoundException(long id) {
        super("Bank account with id " + id + " doesn't exist ");
    }
}
