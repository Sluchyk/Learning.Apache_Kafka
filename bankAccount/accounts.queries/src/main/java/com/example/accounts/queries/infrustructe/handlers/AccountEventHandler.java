package com.example.accounts.queries.infrustructe.handlers;

import com.example.account.common.events.AccountClosedEvent;
import com.example.account.common.events.AccountOpenedEvent;
import com.example.account.common.events.FundsDepositEvent;
import com.example.account.common.events.FundsWithdrawEvent;
import com.example.accounts.queries.domaine.AccountRepository;
import com.example.accounts.queries.domaine.BankAccount;
import org.springframework.stereotype.Service;

@Service
public class AccountEventHandler implements EventHandler{
    private final AccountRepository repository;

    public AccountEventHandler(AccountRepository repository) {
        this.repository = repository;
    }

    @Override
    public void on(AccountOpenedEvent event) {
        var bankAccount = BankAccount.builder()
                .id(event.getId())
                .accountHolder(event.getAccountHolder())
                .creationDate(event.getCreatedDate())
                .accountType(event.getAccountType())
                .balance(event.getOpeningBalance())
                .build();
        repository.save(bankAccount);
    }
    @Override
    public void on(FundsDepositEvent event) {
        var bankAccount = repository.findById(event.getId());
        if(bankAccount.isEmpty()){
            return;
        }
        var currentBalance = bankAccount.get().getBalance();
        var latestBalance = currentBalance + event.getDepositEmount();
        bankAccount.get().setBalance(latestBalance);
        repository.save(bankAccount.get());
    }
    @Override
    public void on(FundsWithdrawEvent event) {
        var bankAccount = repository.findById(event.getId());
        if(bankAccount.isEmpty()){
            return;
        }
        var currentBalance = bankAccount.get().getBalance();
        var latestBalance = currentBalance - event.getAmountWithdraw();
        bankAccount.get().setBalance(latestBalance);
        repository.save(bankAccount.get());
    }
    @Override
    public void on(AccountClosedEvent event) {
        repository.deleteById(event.getId());
    }
}
