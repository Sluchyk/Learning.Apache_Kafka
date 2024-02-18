package com.example.accounts.queries.api.dto;

import com.example.account.common.dto.BaseResponse;
import com.example.accounts.queries.domaine.BankAccount;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class AccountLookUpResponse extends BaseResponse {
    private List<BankAccount> accounts;
    public AccountLookUpResponse(String message){
        super(message);
    }
}
