package com.springbank.bankacc.query.api.handlers;

import com.springbank.bankacc.core.events.ClosedAccountEvent;
import com.springbank.bankacc.core.events.DepositedFundsEvent;
import com.springbank.bankacc.core.events.OpenedAccountEvent;
import com.springbank.bankacc.core.events.WithdrawedFundEvent;
import com.springbank.bankacc.core.models.BankAccount;
import com.springbank.bankacc.query.api.repository.AccountRepository;
import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@ProcessingGroup("bankaccount-group")
public class AccountEventsHandlerImpl implements AccountEventsHandler {

    private AccountRepository accountRepository;

    @Autowired
    public AccountEventsHandlerImpl(AccountRepository accountRepository){
        this.accountRepository = accountRepository;
    }

    @EventHandler
    @Override
    public void on(OpenedAccountEvent event) {
       BankAccount bankAccountObj= BankAccount.builder()
                .id(event.getId())
                .accountHolderId(event.getAccountHolderId())
                .accountType(event.getAccountType())
                .creationDate(event.getCreationDate())
                .balance(event.getOpeningBalance())
                .build();
       accountRepository.save(bankAccountObj);
    }

    @EventHandler
    @Override
    public void on(DepositedFundsEvent event) {
        Optional<BankAccount> bankAccount = accountRepository.findById(event.getId());
        if(!bankAccount.isPresent()) {
            return;
        }
        bankAccount.get().setBalance(event.getBalance());
        accountRepository.save(bankAccount.get());
    }

    @EventHandler
    @Override
    public void on(WithdrawedFundEvent event) {
        Optional<BankAccount> bankAccount = accountRepository.findById(event.getId());
        if(!bankAccount.isPresent()) {
            return;
        }
        bankAccount.get().setBalance(event.getBalance());
        accountRepository.save(bankAccount.get());
    }

    @EventHandler
    @Override
    public void on(ClosedAccountEvent event) {
        accountRepository.deleteById(event.getId());
    }

}
