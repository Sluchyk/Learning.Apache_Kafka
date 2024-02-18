package com.example.accounts.cmd;

import com.example.accounts.cmd.api.commands.ClosedAccountCommand;
import com.example.accounts.cmd.api.commands.CommandHandler;
import com.example.accounts.cmd.api.commands.DepositFundsCommand;
import com.example.accounts.cmd.api.commands.OpenAccountCommand;
import com.example.accounts.cmd.api.commands.RestoreReadDbCommand;
import com.example.accounts.cmd.api.commands.WithdrawFundsCommand;
import com.example.sqrs.core.infrastructure.CommandDispatcher;
import jakarta.annotation.PostConstruct;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CommandApplication {

	private final CommandDispatcher commandDispatcher;
	private final CommandHandler commandHandler;
	public CommandApplication(CommandDispatcher commandDispatcher, CommandHandler commandHandler) {
		this.commandDispatcher = commandDispatcher;
		this.commandHandler = commandHandler;
	}

	public static void main(String[] args) {
		SpringApplication.run(CommandApplication.class, args);
	}
	@PostConstruct
	public  void  registerHandlers(){
		commandDispatcher.registerHandler(OpenAccountCommand.class,commandHandler::handle);
		commandDispatcher.registerHandler(DepositFundsCommand.class,commandHandler::handle);
		commandDispatcher.registerHandler(WithdrawFundsCommand.class,commandHandler::handle);
		commandDispatcher.registerHandler(ClosedAccountCommand.class,commandHandler::handle);
		commandDispatcher.registerHandler(RestoreReadDbCommand.class,commandHandler::handle);
	}

}

