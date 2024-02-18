package com.example.accounts.cmd.api.commands;

import com.example.sqrs.core.commands.BaseCommand;
import lombok.Data;

@Data
public class DepositFundsCommand extends BaseCommand {
    private  double amount;
}
