package com.example.accounts.cmd.api.commands;

import com.example.accounts.cmd.domaine.AccountAggregate;
import com.example.sqrs.core.handlers.EventSourcingHandler;
import org.springframework.stereotype.Service;

@Service
public class AccountCommandHandler implements CommandHandler{
    private final EventSourcingHandler<AccountAggregate> accountAggregateEventSourcingHandler;

    public AccountCommandHandler(EventSourcingHandler<AccountAggregate> accountAggregateEventSourcingHandler) {
        this.accountAggregateEventSourcingHandler = accountAggregateEventSourcingHandler;
    }

    @Override
    public void handle(OpenAccountCommand command) {
     var aggregate = new AccountAggregate(command) ;
     accountAggregateEventSourcingHandler.save(aggregate);
    }

    @Override
    public void handle(DepositFundsCommand command) {
        var aggregate  = accountAggregateEventSourcingHandler.getById(command.getId());
        aggregate.depositFunds(command.getAmount());
        accountAggregateEventSourcingHandler.save(aggregate);
    }

    @Override
    public void handle(WithdrawFundsCommand command) {
        var aggregate = accountAggregateEventSourcingHandler.getById(command.getId());
        if(command.getAmount() > aggregate.getBalance()){
            throw new IllegalStateException("Withdraw declined,insufficient funds");
        }
        aggregate.withdrawFunds(command.getAmount());
        accountAggregateEventSourcingHandler.save(aggregate);
    }

    @Override
    public void handle(ClosedAccountCommand command) {
        var aggregate = accountAggregateEventSourcingHandler.getById(command.getId());
        aggregate.closedAccount();
        accountAggregateEventSourcingHandler.save(aggregate);
    }

    @Override
    public void handle(RestoreReadDbCommand command) {
        accountAggregateEventSourcingHandler.republishEvents();
    }
}
