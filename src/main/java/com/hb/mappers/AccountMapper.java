package com.hb.mappers;


import com.hb.dto.responses.AccountViewResponse;
import com.hb.model.documents.Account;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class AccountMapper {
    public AccountViewResponse toView(Account account){
        return AccountViewResponse.builder()
                .id(account.getId())
                .username(account.getUsername())
                .password(account.getPassword())
                .createdTime(account.getCreatedTime())
                .build();
    }

    public AccountViewResponse optionalToView(Optional<Account> optionalAccount) {
        return AccountViewResponse.builder()
                .id(optionalAccount.get().getId())
                .username(optionalAccount.get().getUsername())
                .password(optionalAccount.get().getPassword())
                .createdTime(optionalAccount.get().getCreatedTime())
                .build();
    }
}
