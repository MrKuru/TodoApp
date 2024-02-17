package com.hb.dto.requests;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;



@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateAccountRequest {
    @NotNull
    private Long id;
    @NotNull
    @Size(min = 5, max = 15)
    private String username;

    @NotNull
    @Size(min = 5, max = 15)
    private String password;
}

