package com.example.accounts.queries.api.queries;

import com.example.sqrs.core.queries.BaseQuery;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FindByAccountHolderQuery extends BaseQuery {
    private String accountHolder;
}
