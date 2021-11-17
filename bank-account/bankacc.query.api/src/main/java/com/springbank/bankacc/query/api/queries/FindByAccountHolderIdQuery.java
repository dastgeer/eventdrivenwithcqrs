package com.springbank.bankacc.query.api.queries;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FindByAccountHolderIdQuery {
    private String id;
}
