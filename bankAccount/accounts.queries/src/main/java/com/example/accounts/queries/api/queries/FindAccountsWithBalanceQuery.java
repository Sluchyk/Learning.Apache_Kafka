package com.example.accounts.queries.api.queries;

import com.example.accounts.queries.api.dto.EqualityType;
import com.example.sqrs.core.queries.BaseQuery;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FindAccountsWithBalanceQuery extends BaseQuery {
    private EqualityType equalityType;
    private double balance;
}
