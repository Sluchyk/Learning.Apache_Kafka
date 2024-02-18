package com.example.accounts.cmd.api.commands;

public interface CommandHandler {
    void handle(OpenAccountCommand command);
    void handle(DepositFundsCommand command);
    void handle(WithdrawFundsCommand command);
    void handle(ClosedAccountCommand command);
    void handle(RestoreReadDbCommand command);
}
