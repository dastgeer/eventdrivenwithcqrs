package com.springbank.bankacc.core.events;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ClosedAccountEvent {
    private String id;
}
