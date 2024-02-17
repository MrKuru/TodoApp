package com.hb.components;


import com.hb.dto.requests.CreateAccountRequest;
import com.hb.dto.responses.AccountViewResponse;
import com.hb.exception.exceptions.AccountAlreadyExistsException;
import com.hb.exception.exceptions.AccountNotFoundException;
import com.hb.exception.exceptions.AccountWithTodosException;
import com.hb.mappers.AccountMapper;
import com.hb.model.documents.Account;
import com.hb.model.documents.Todo;
import com.hb.model.repositories.AccountRepository;
import com.hb.model.repositories.TodoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class AccountComponent {
    private final TodoRepository todoRepository;
    private final AccountRepository repository;
    private final AccountMapper mapper;
    public AccountViewResponse createAccount(CreateAccountRequest request) {

        Optional<Account> existingAccount = repository.findByUsername(request.getUsername());
        if (existingAccount.isPresent()){
            throw new AccountAlreadyExistsException("The account with this username already exists.");
        }
        Account account = Account.builder()
                .id(request.getId())
                .username(request.getUsername())
                .password(request.getPassword())
                .createdTime(LocalDateTime.now())
                .build();
        repository.save(account);
        return mapper.toView(account);
    }

    public AccountViewResponse getAccountById(Long id) {
        Optional<Account> optionalAccount = repository.findById(id);
        if (optionalAccount.isEmpty()){
            throw new AccountNotFoundException("Account not found with id: " + id);
        }
        return mapper.optionalToView(optionalAccount);
    }

    public void deleteAccount(Long id) {
        List<Todo> todoList = todoRepository.findByAccountId(id);
        if (!todoList.isEmpty()) {
            throw new AccountWithTodosException("Account with id " + id + " has associated todos. Cannot delete.");
        }
        if (repository.existsById(id)) {
            repository.deleteById(id);
        } else {
            throw new AccountNotFoundException("Account not found with id: " + id);
        }
    }
}
