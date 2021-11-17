package com.springbank.bankacc.query.api.handlers;

import com.springbank.bankacc.core.models.BankAccount;
import com.springbank.bankacc.query.api.repository.AccountRepository;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AccountQueryHandlerImpl implements AccountQueryHandler {
    private AccountRepository accountRepository;

    @Autowired
    public AccountQueryHandlerImpl(AccountRepository accountRepository){
        this.accountRepository = accountRepository;
    }

    @QueryHandler
    @Override
    public AccountLookUpResponse findAccountById(FindAccountByIdQuery query) {
        Optional<BankAccount> byIdResponse = accountRepository.findById(query.getId());
        if(!byIdResponse.isPresent()){
            return new AccountLookUpResponse("no result found with id "+query.getId());
        }
        return new AccountLookUpResponse(" result found with id "+query.getId(),byIdResponse.get());
    }

    @QueryHandler
    @Override
    public AccountLookUpResponse findAllAccounts(FindAllAccountsQuery query) {
        Iterable<BankAccount> allAccount = accountRepository.findAll();
        if(!allAccount.iterator().hasNext()){
            return new AccountLookUpResponse("no result found ");
        }
        List<BankAccount> accList = new ArrayList<>();
        allAccount.iterator().forEachRemaining(account-> accList.add(account));
        return new AccountLookUpResponse("result found with size ="+accList.size(),accList);
    }

    @QueryHandler
    @Override
    public AccountLookUpResponse findAccountByHolderId(FindByAccountHolderIdQuery query) {
        Optional<BankAccount> byIdResponse = accountRepository.findByAccountHolderId(query.getId());
        if(!byIdResponse.isPresent()){
            return new AccountLookUpResponse("no result found with id "+query.getId());
        }
        return new AccountLookUpResponse(" result found with id "+query.getId(),byIdResponse.get());
    }


    @QueryHandler
    @Override
    public AccountLookUpResponse findAccountWithBalance(FindAccountWithBalanceQuery query) {

        List<BankAccount> bankAccounts = query.getEqualityType() == EqualityType.GREATER_THAN ? accountRepository.findByBalanceGreaterThan(query.getBalance()) :
                accountRepository.findByBalanceLessThan(query.getBalance());
        if(bankAccounts== null || bankAccounts.size()==0){
            return new AccountLookUpResponse(" no result found with balance"+query.getEqualityType());
        }
        return new AccountLookUpResponse(" result found with balance"+query.getEqualityType(),bankAccounts);
    }
}
