package com.example.accounts.queries.api.queries;

import com.example.sqrs.core.domaine.BaseEntity;
import java.util.List;

public interface QueryHandler {
    List<BaseEntity> handle(FindAllAccountQuery query);
    List<BaseEntity> handle(AccountsByIdQuery query);
    List<BaseEntity> handle(FindAccountsWithBalanceQuery query);
    List<BaseEntity> handle(FindByAccountHolderQuery query);
}
