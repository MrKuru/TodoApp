package com.hb.services;


import com.hb.dto.requests.CreateAccountRequest;
import com.hb.dto.responses.AccountViewResponse;
import jakarta.validation.Valid;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;



@Validated
public interface AccountService {
    @PostMapping("/account")
    AccountViewResponse createAccount(@RequestBody @Valid CreateAccountRequest request);
    @GetMapping("/account/{id}")
    AccountViewResponse getAccountById(@PathVariable Long id);
    @DeleteMapping("/account/{id}")
    void deleteAccount(@PathVariable Long id);
}
