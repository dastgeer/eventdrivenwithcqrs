package com.springbank.bankacc.cmd.api.aggregates;

import com.springbank.bankacc.cmd.api.commands.CloseAccountCommand;
import com.springbank.bankacc.cmd.api.commands.DepositFundCommand;
import com.springbank.bankacc.cmd.api.commands.OpenAccountCommand;
import com.springbank.bankacc.cmd.api.commands.WithdrawFundCommand;
import com.springbank.bankacc.core.events.ClosedAccountEvent;
import com.springbank.bankacc.core.events.DepositedFundsEvent;
import com.springbank.bankacc.core.events.OpenedAccountEvent;
import com.springbank.bankacc.core.events.WithdrawedFundEvent;
import lombok.NoArgsConstructor;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;

import java.util.Date;

@Aggregate
@NoArgsConstructor
public class AccountAggregate {
    @AggregateIdentifier
    private String id;
    private String accountHolderId;
    private double balance;

    @CommandHandler
    public AccountAggregate(OpenAccountCommand command){
        OpenedAccountEvent event = OpenedAccountEvent.builder()
                                                    .id(command.getId())
                                                    .accountHolderId(command.getAccountHolderId())
                                                    .creationDate(new Date())
                                                    .accountType(command.getAccountType())
                                                    .openingBalance(command.getBalance())
                                                    .build();
        AggregateLifecycle.apply(event);
    }

    @EventSourcingHandler
    public void on(OpenedAccountEvent event){
         this.id = event.getId();
         this.accountHolderId = event.getAccountHolderId();
         this.balance = event.getOpeningBalance();
    }

    @CommandHandler
    public void handle(DepositFundCommand command){
       double amount = command.getAmount();
        DepositedFundsEvent event= DepositedFundsEvent.builder().id(command.getId())
                                        .amount(amount)
                                        .balance(this.balance+amount)
                                        .build();
        AggregateLifecycle.apply(event);
    }

    @EventSourcingHandler
    public void on(DepositedFundsEvent event){
        this.balance = this.balance+event.getAmount();
    }

    @CommandHandler
    public void handle(WithdrawFundCommand command){
        double amount = command.getAmount();
        if(this.balance-amount<0){
            throw new IllegalArgumentException("insufficient amount in balance!!");
        }
       WithdrawedFundEvent event= WithdrawedFundEvent.builder().id(command.getId()).amount(amount).balance(this.balance-amount).build();
        AggregateLifecycle.apply(event);
    }

    @EventSourcingHandler
    public void on(WithdrawedFundEvent event){
        this.balance = this.balance- event.getAmount();
    }

    @CommandHandler
    public void handle(CloseAccountCommand command){
        ClosedAccountEvent event= ClosedAccountEvent.builder().id(command.getId()).build();
        AggregateLifecycle.apply(event);
    }

    @EventSourcingHandler
    public void on(ClosedAccountEvent event){
        AggregateLifecycle.markDeleted();
    }

}
