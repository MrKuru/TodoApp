package com.hb.controllers;


import com.hb.components.AccountComponent;
import com.hb.dto.requests.CreateAccountRequest;
import com.hb.dto.responses.AccountViewResponse;
import com.hb.services.AccountService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;




@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/1.0/account")
@SecurityRequirement(name = "basicAuth")
public class AccountController implements AccountService {
    private final AccountComponent accountComponent;
    @Override
    public AccountViewResponse createAccount(@Valid CreateAccountRequest request) {
        AccountViewResponse response = accountComponent.createAccount(request);
        return response;
    }

    @Override
    public AccountViewResponse getAccountById(Long id) {
        AccountViewResponse response = accountComponent.getAccountById(id);
        return response;
    }

    @Override
    public void deleteAccount(Long id) {
        accountComponent.deleteAccount(id);
    }
}
