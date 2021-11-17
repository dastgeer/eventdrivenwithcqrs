package com.springbank.bankacc.cmd.api.commands;

import com.springbank.bankacc.core.models.AccountType;
import lombok.Builder;
import lombok.Data;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
@Builder
public class OpenAccountCommand {
    @TargetAggregateIdentifier
    private String id;

    @NotNull(message = "account holder vlue should not be empty")
    private String accountHolderId;
    @NotNull(message = "account type vlue should not be empty")
    private AccountType accountType;

    @Min(value = 0,message = "openeing account must be have 1000")
    private double balance;
}
