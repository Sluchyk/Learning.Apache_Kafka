package com.example.accounts.queries;

import com.example.accounts.queries.api.queries.AccountsByIdQuery;
import com.example.accounts.queries.api.queries.FindAccountsWithBalanceQuery;
import com.example.accounts.queries.api.queries.FindAllAccountQuery;
import com.example.accounts.queries.api.queries.FindByAccountHolderQuery;
import com.example.accounts.queries.api.queries.QueryHandler;
import com.example.accounts.queries.infrustructe.QueryDispatcher;
import jakarta.annotation.PostConstruct;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class QueriesApplication {
private final QueryHandler queryHandler;
private final QueryDispatcher queryDispatcher;

	public QueriesApplication(QueryDispatcher queryDispatcher, QueryHandler queryHandler) {
		this.queryDispatcher = queryDispatcher;
		this.queryHandler = queryHandler;
	}

	public static void main(String[] args) {
		SpringApplication.run(QueriesApplication.class, args);
	}
	@PostConstruct
	public void registerHandlers(){
		queryDispatcher.registerHandler(FindAllAccountQuery.class,queryHandler::handle);
		queryDispatcher.registerHandler(AccountsByIdQuery.class,queryHandler::handle);
		queryDispatcher.registerHandler(FindByAccountHolderQuery.class,queryHandler::handle);
		queryDispatcher.registerHandler(FindAccountsWithBalanceQuery.class,queryHandler::handle);
	}
}
