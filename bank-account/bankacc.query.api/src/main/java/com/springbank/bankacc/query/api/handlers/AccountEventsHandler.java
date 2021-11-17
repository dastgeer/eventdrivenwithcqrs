package com.springbank.bankacc.query.api.handlers;

import com.springbank.bankacc.core.events.ClosedAccountEvent;
import com.springbank.bankacc.core.events.DepositedFundsEvent;
import com.springbank.bankacc.core.events.OpenedAccountEvent;
import com.springbank.bankacc.core.events.WithdrawedFundEvent;

public interface AccountEventsHandler {
    public void on(OpenedAccountEvent event);
    public void on(DepositedFundsEvent event);
    public void on(WithdrawedFundEvent event);
    public void on(ClosedAccountEvent event);
}
