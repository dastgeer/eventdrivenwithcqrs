package com.springbank.bankacc.query.api.handlers;

public interface AccountQueryHandler {

    public AccountLookUpResponse findAccountById(FindAccountByIdQuery query);
    public AccountLookUpResponse findAllAccounts(FindAllAccountsQuery query);
    public AccountLookUpResponse findAccountByHolderId(FindByAccountHolderIdQuery query);
    public AccountLookUpResponse findAccountWithBalance(FindAccountWithBalanceQuery query);
}
