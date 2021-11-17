package com.springbank.bankacc.query.api.dto;

import com.springbank.bankacc.core.dto.BaseResponse;
import com.springbank.bankacc.core.models.BankAccount;

import java.util.ArrayList;
import java.util.List;

public class AccountLookUpResponse extends BaseResponse {

    private List<BankAccount> bankAccounts;

    public AccountLookUpResponse(String message) {
        super(message);
    }

    public AccountLookUpResponse(String message, List<BankAccount> bankAccounts) {
        super(message);
        this.bankAccounts = bankAccounts;
    }

    public AccountLookUpResponse(String message, BankAccount bankAccount) {
        super(message);

        this.bankAccounts = new ArrayList<>();
        this.bankAccounts.add(bankAccount);
    }

    public List<BankAccount> getBankAccounts() {
        return bankAccounts;
    }

    public void setBankAccounts(List<BankAccount> bankAccounts) {
        this.bankAccounts = bankAccounts;
    }
}
