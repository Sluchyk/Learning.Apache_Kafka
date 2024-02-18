package com.example.accounts.queries.infrustructe.handlers;

import com.example.account.common.events.AccountClosedEvent;
import com.example.account.common.events.AccountOpenedEvent;
import com.example.account.common.events.FundsDepositEvent;
import com.example.account.common.events.FundsWithdrawEvent;

public interface EventHandler {
    void on(AccountOpenedEvent event);
    void on(FundsDepositEvent event);
    void on(FundsWithdrawEvent event);
    void on(AccountClosedEvent event);
}
