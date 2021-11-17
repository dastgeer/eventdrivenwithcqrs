package com.springbank.bankacc.core.events;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class WithdrawedFundEvent {
    private String id;
    private double amount;
    private double balance;
}
