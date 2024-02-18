package com.example.accounts.queries.api.queries;

import com.example.accounts.queries.api.dto.EqualityType;
import com.example.accounts.queries.domaine.AccountRepository;
import com.example.accounts.queries.domaine.BankAccount;
import com.example.sqrs.core.domaine.BaseEntity;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class AccountQueryHandler implements QueryHandler {
    private final AccountRepository accountRepository;

    public AccountQueryHandler(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public List<BaseEntity> handle(FindAllAccountQuery query) {
        Iterable<BankAccount> bankAccounts = accountRepository.findAll();
        List<BaseEntity> baseEntities = new ArrayList<>();
        bankAccounts.forEach(baseEntities::add);
        return baseEntities;
    }

    @Override
    public List<BaseEntity> handle(AccountsByIdQuery query) {
        var bankAccount = accountRepository.findById(query.getId());
        if (bankAccount.isEmpty()) {
            return null;
        }
        List<BaseEntity> baseEntities = new ArrayList<>();
        baseEntities.add(bankAccount.get());
        return baseEntities;
    }

    @Override
    public List<BaseEntity> handle(FindAccountsWithBalanceQuery query) {
        return query.getEqualityType() == EqualityType.GREATER_THAN
                ? accountRepository.findByBalanceGreaterThan(query.getBalance()) : accountRepository.findByBalanceLessThan(query.getBalance());
    }

    @Override
    public List<BaseEntity> handle(FindByAccountHolderQuery query) {
        var bankAccount = accountRepository.findByAccountHolder(query.getAccountHolder());
        if (bankAccount.isEmpty()) {
            return null;
        }
        return bankAccount;
    }
}
