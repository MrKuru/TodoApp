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
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AccountComponentTest {

    @Mock
    private AccountRepository mockAccountRepository;
    @Mock
    private TodoRepository mockTodoRepository;
    @Mock
    private AccountMapper mockAccountMapper;

    @InjectMocks
    private AccountComponent mockAccountComponent;


    @Test
    void createAccount_Success() {
        CreateAccountRequest request = new CreateAccountRequest(1L,"username", "password");
        when(mockAccountRepository.findByUsername("username")).thenReturn(Optional.empty());
        when(mockAccountMapper.toView(any(Account.class))).thenReturn(new AccountViewResponse());

        AccountViewResponse response = mockAccountComponent.createAccount(request);

        Mockito.verify(mockAccountRepository).findByUsername("username");
        Mockito.verify(mockAccountRepository).save(any(Account.class));
        Mockito.verify(mockAccountMapper).toView(any(Account.class));
        assertThat(response).isNotNull();
    }

    @Test
    void createAccount_AccountAlreadyExistsException() {
        CreateAccountRequest request = new CreateAccountRequest(1L,"existingUsername", "password");
        when(mockAccountRepository.findByUsername("existingUsername")).thenReturn(Optional.of(new Account()));

        assertThatThrownBy(() -> mockAccountComponent.createAccount(request))
                .isInstanceOf(AccountAlreadyExistsException.class);
    }

    @Test
    void getAccountById_Success() {
        Account account = new Account();
        when(mockAccountRepository.findById(anyLong())).thenReturn(Optional.of(account));
        when(mockAccountMapper.optionalToView(Optional.of(account))).thenReturn(new AccountViewResponse());

        AccountViewResponse response = mockAccountComponent.getAccountById(anyLong());

        Mockito.verify(mockAccountRepository).findById(anyLong());
        Mockito.verify(mockAccountMapper).optionalToView(Optional.of(account));
        assertThat(response).isNotNull();
    }

    @Test
    void getAccountById_AccountNotFoundException() {
        when(mockAccountRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThatThrownBy(() -> mockAccountComponent.getAccountById(anyLong()))
                .isInstanceOf(AccountNotFoundException.class);
    }

    @Test
    void deleteAccount_Success() {
        Long accountId = 1L;
        when(mockTodoRepository.findByAccountId(accountId)).thenReturn(List.of());
        when(mockAccountRepository.existsById(accountId)).thenReturn(true);

        mockAccountComponent.deleteAccount(accountId);

        Mockito.verify(mockTodoRepository).findByAccountId(accountId);
        Mockito.verify(mockAccountRepository).existsById(accountId);
        Mockito.verify(mockAccountRepository).deleteById(accountId);
    }

    @Test
    void deleteAccount_AccountWithTodosException() {
        Long accountId = 2L;
        when(mockTodoRepository.findByAccountId(accountId)).thenReturn(List.of(new Todo()));

        assertThatThrownBy(() -> mockAccountComponent.deleteAccount(accountId))
                .isInstanceOf(AccountWithTodosException.class);
    }

    @Test
    void deleteAccount_AccountNotFoundException() {
        Long accountId = 3L;
        when(mockTodoRepository.findByAccountId(accountId)).thenReturn(List.of());
        when(mockAccountRepository.existsById(accountId)).thenReturn(false);

        assertThatThrownBy(() -> mockAccountComponent.deleteAccount(accountId))
                .isInstanceOf(AccountNotFoundException.class);
    }
}