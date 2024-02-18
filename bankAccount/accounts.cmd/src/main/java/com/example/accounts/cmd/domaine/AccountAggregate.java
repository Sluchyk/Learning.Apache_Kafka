package com.example.accounts.cmd.domaine;

import com.example.account.common.events.AccountClosedEvent;
import com.example.account.common.events.AccountOpenedEvent;
import com.example.account.common.events.FundsDepositEvent;
import com.example.account.common.events.FundsWithdrawEvent;
import com.example.accounts.cmd.api.commands.OpenAccountCommand;
import com.example.sqrs.core.domaine.AggregateRoot;
import java.util.Date;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class AccountAggregate extends AggregateRoot {
    private Boolean isActive;
    private  double balance;
    public  AccountAggregate(OpenAccountCommand command){
        raiseEvent(AccountOpenedEvent.builder()
                .id(command.getId())
                .accountHolder(command.getAccountHolder())
                .createdDate(new Date())
                .accountType(command.getAccountType())
                .openingBalance(command.getOpeningBalance())
                .build());
    }
    public void apply(AccountOpenedEvent event){
        this.id = event.getId();
        this.isActive = true;
        this.balance = event.getOpeningBalance();
    }
    public  void depositFunds(double amount){
        if(!this.isActive){
            throw  new IllegalStateException("Funds cannot be deposited in a closed account");
        }
        if(amount <= 0){
            throw  new IllegalStateException("The deposit must be greater then 0");
        }
        raiseEvent(FundsDepositEvent.builder()
                .id(this.id)
                .depositEmount(amount)
                .build());
    }
    public void apply(FundsDepositEvent event){
        this.id = event.getId();
        this.balance += event.getDepositEmount();
    }
    public void withdrawFunds(double amount){
        if(!this.isActive){
            throw  new IllegalStateException("Funds cannot be withdrew from closed account");
        }
        raiseEvent(FundsWithdrawEvent.builder()
                .id(this.id)
                .amountWithdraw(amount)
                .build());
    }
    public void apply(FundsWithdrawEvent event){
        this.id = event.getId();
        this.balance -= event.getAmountWithdraw();
    }
    public void closedAccount(){
        if(!isActive){
            throw  new IllegalStateException("The bank account have been closed already");
        }
        raiseEvent(AccountClosedEvent.builder()
                .id(this.id)
                .build());
    }
    public void apply(AccountClosedEvent event){
        this.id = event.getId();
        this.isActive = false;
    }
    public double getBalance(){
        return this.balance;
    }
    public Boolean getIsActive(){
        return this.isActive;
    }
}
